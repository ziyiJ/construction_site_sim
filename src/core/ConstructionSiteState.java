package core;

import io.DeliverySchedule;
import io.SiteLayout;

import java.io.File;
import java.io.IOException;

import org.joda.time.LocalDateTime;

import ec.util.Parameter;
import ec.util.ParameterDatabase;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Properties;
import util.TimeConvert;

public class ConstructionSiteState extends SimState {
	// The physical construction site
	public Continuous2D siteArea;

	private static ParameterDatabase paramDB;
	
	public static SiteLayout siteLayout;
	public static DeliverySchedule deliverySchedule;

	public static final String CMD_CONF_FILE = "-conf";
	public static final String CMD_LAYOUT_DIR = "-layout";
	public static final String CMD_SCHDULE_FILE = "-schedule";
	// Parameter configuration keywords
	public static final String P_SEED = "seed";
	public static final String P_STIME = "StartTime";
	public static final String V_TIME = "time";
	public static final String P_FORKLIFT_NO = "ForkLiftNum";
	
	// Simulation parameters
	private long simSeed = 12345;
	private double siteWidth = 100;
	private double siteHeigh = 100;
	private LocalDateTime startTime = new LocalDateTime();
	private int forkLiftNum = 2;

	// 
	public AgentRegistry agentReg = new AgentRegistry();

	private static final long serialVersionUID = -1262505079990903205L;

	// to be used with GUI
	public ConstructionSiteState(long seed, String[] args) {
		super(seed);
		connectParams(args);
	}

	public ConstructionSiteState(long seed) {
		super(seed);
	}

	public ConstructionSiteState(ParameterDatabase db) {
		super(1);
		paramDB = db;
	}

	// to be used with CMD
	public static void doLoop(String[] args) {
		connectParams(args);
		doLoop(ConstructionSiteState.class, args);
	}

	public void start() {
		loadParams();
		
		super.start();

		siteArea = new Continuous2D(1.0, siteWidth, siteHeigh);

		siteArea.clear();

		ScenarioGenerator.populatePlaces(this);
		
		ScenarioGenerator.populateVehicles(this);
	}

	public void finish() {
		super.finish();
	}

	private static void connectParams(String[] args) {
		for (int x = 0; x < args.length - 1; x++) {
			if (args[x].equals(CMD_CONF_FILE)) {
				try {
					paramDB = new ParameterDatabase(
							new File(args[x + 1]).getAbsoluteFile(), args);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
			if (args[x].equals(CMD_LAYOUT_DIR)) {
				siteLayout = new SiteLayout(args[x + 1]);
			}

			if (args[x].equals(CMD_SCHDULE_FILE)) {
				deliverySchedule = new DeliverySchedule(args[x + 1]);
			}
		}
	}

	private void loadParams() {
		if (paramDB == null)
			return;
		
		Parameter param;
		String s = paramDB.getString(param = new Parameter(P_SEED), null);
		if (s.equalsIgnoreCase(V_TIME))
			simSeed = System.currentTimeMillis();
		else
			simSeed = paramDB.getLong(param);
		random.setSeed(simSeed);
		
		s = paramDB.getString(new Parameter(P_STIME), null);
		if (s != null) {
			try {
				startTime = LocalDateTime.parse(s);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		Properties p = Properties.getProperties(this, false, true, false, true);

		for (int i = 0; i < p.numProperties(); i++) {
			if (p.isReadWrite(i) && !p.isComposite(i)
					&& paramDB.exists(new Parameter(p.getName(i)))) {
				Parameter pa = new Parameter(p.getName(i));
				String value = paramDB.getString(pa, null);
				p.setValue(i, value);
			}
		}
	}
	
	public LocalDateTime startTime() {
		return startTime;
	}
	
	public LocalDateTime currentTime() {
		return TimeConvert.SimT2CivilT(startTime, schedule.getTime());
	}
	
	public Continuous2D getArea() {
		return siteArea;
	}

	public long getSimSeed() {
		return simSeed;
	}

	public void setSimSeed(long simSeed) {
		this.simSeed = simSeed;
	}

	public double getSiteWidth() {
		return siteWidth;
	}

	public void setSiteWidth(double siteWidth) {
		this.siteWidth = siteWidth;
	}

	public double getSiteHeigh() {
		return siteHeigh;
	}

	public void setSiteHeigh(double siteHeigh) {
		this.siteHeigh = siteHeigh;
	}

	public int getForkLiftNum() {
		return forkLiftNum;
	}

	public void setForkLiftNum(int forkLiftNum) {
		this.forkLiftNum = forkLiftNum;
	}
}
