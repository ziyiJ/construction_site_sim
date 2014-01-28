package io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.ConstructionSiteState;
import core.Place;
import objects.Entrance;
import objects.Exit;
import objects.TempStorage;
import objects.UnloadingBay;
import objects.WorkSite;
import sim.util.Double2D;

public class SiteLayout {
	
	private File siteLayoutPath; 
	private File worksitesLayout;
	private File tempstoragesLayout;
	private File unloadingbaysLayout;
	private File siteentranceLayout;
	private File siteexitLayout;
	
	private static final String WORKSITES_FILE="worksites";
	private static final String TEMPSTORAGES_FILE="tempstorages";
	private static final String UNLOADINGBAYS_FILE="unloadingbays";
	private static final String SITEENTRANCE_FILE="siteentrances";
	private static final String SITEEXIT_FILE="siteexits";
	
	private static final int worksites_file_fields = 5;
	private static final int unloadingbays_file_fields = 6;
	private static final int temstorages_file_fields = 5;
	private static final int siteentrance_file_fields = 5;
	private static final int siteexit_file_fields = 5;
	
	public SiteLayout(String path) {
		siteLayoutPath = new File(path).getAbsoluteFile();
		worksitesLayout = new File(siteLayoutPath, WORKSITES_FILE);
		tempstoragesLayout = new File(siteLayoutPath, TEMPSTORAGES_FILE);
		unloadingbaysLayout = new File(siteLayoutPath, UNLOADINGBAYS_FILE);
		siteentranceLayout = new File(siteLayoutPath, SITEENTRANCE_FILE);
		siteexitLayout = new File(siteLayoutPath, SITEEXIT_FILE);
	}
	
	// A general agent production methods
	private <T extends Place> List<T> genAgents(ConstructionSiteState site, File layout, int fields, 
			Class<T> type) {
		List<T> agents = new ArrayList<T>();

		try {
			List<String[]> content = CSVFile.readCSV(layout, fields);
			for (String[] line : content) {
				T new_agent = type.newInstance();
				new_agent.setID(Integer.parseInt(line[1]));
				new_agent.setSiteState(site);
				// TODO: a better way to have different site with different rate of working!!
				new_agent.startWorking(5000);
				new_agent.setPositionAndOrientation(
						new Double2D(Double.parseDouble(line[2]), Double.parseDouble(line[3])), 
						Double.parseDouble(line[4]));

				System.out.println("Adding " + new_agent.toString() + " @ (" 
						+ new_agent.getPosition().x + ", "
						+ new_agent.getPosition().y + ")");

				agents.add(new_agent);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return agents;
	}
	
	public List<WorkSite> genWorkSites(ConstructionSiteState site) {
		return genAgents(site, worksitesLayout, worksites_file_fields, WorkSite.class);
	}
	
	public List<TempStorage> genTempStorages(ConstructionSiteState site) {
		return genAgents(site, tempstoragesLayout, temstorages_file_fields, TempStorage.class);
	}

	public List<UnloadingBay> genUnloadingBays(ConstructionSiteState site) {
		return genAgents(site, unloadingbaysLayout, unloadingbays_file_fields, UnloadingBay.class);
	}
	
	public List<Entrance> genSiteEntrances(ConstructionSiteState site) {
		return genAgents(site, siteentranceLayout, siteentrance_file_fields, Entrance.class);
	}
	
	public List<Exit> genSiteExits(ConstructionSiteState site) {
		return genAgents(site, siteexitLayout, siteexit_file_fields, Exit.class);
	}
	
}
