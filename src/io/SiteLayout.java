package io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import core.ConstructionSiteState;

import objects.Gate;
import objects.TempStorage;
import objects.UnloadingBay;
import objects.WorkSite;
import util.MutableSE2;

public class SiteLayout {
	
	private File siteLayoutPath; 
	private File worksitesLayout;
	private File tempstoragesLayout;
	private File unloadingbaysLayout;
	private File siteentranceLayout;
	
	private static final String WORKSITES_FILE="worksites";
	private static final String TEMPSTORAGES_FILE="tempstorages";
	private static final String UNLOADINGBAYS_FILE="unloadingbays";
	private static final String SITEENTRANCE_FILE="siteentrances";
	private static final String SITEEXIT_FILE="siteentrances";
	
	private static final int worksites_file_fields = 5;
	private static final int unloadingbays_file_fields = 6;
	private static final int temstorages_file_fields = 5;
	private static final int siteentrance_file_fields = 5;
	
	public SiteLayout(String path) {
		siteLayoutPath = new File(path).getAbsoluteFile();
		worksitesLayout = new File(siteLayoutPath, WORKSITES_FILE);
		tempstoragesLayout = new File(siteLayoutPath, TEMPSTORAGES_FILE);
		unloadingbaysLayout = new File(siteLayoutPath, UNLOADINGBAYS_FILE);
		siteentranceLayout = new File(siteLayoutPath, SITEENTRANCE_FILE);
	}
	
	public WorkSite[] genWorkSites(ConstructionSiteState site) throws IOException {
		List<String[]> content = CSVFile.readCSV(worksitesLayout, worksites_file_fields);
		WorkSite[] sites = new WorkSite[content.size()];
		for(int i = 0; i != content.size(); ++i) {
			String[] line = content.get(i);
			sites[i] = new WorkSite(Integer.parseInt(line[1]));
			MutableSE2 pos = new MutableSE2(Double.parseDouble(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]));
			sites[i].setSiteState(site);
			sites[i].setPositionAndOrientation(pos);

			System.out.println("Adding " + sites[i].toString() + " @ (" 
					+ sites[i].getPositionAndOrientation().getPosition().x + ", "
					+ sites[i].getPositionAndOrientation().getPosition().y + ")");
		}
		return sites;
	}

	public TempStorage[] genTempStorages(ConstructionSiteState site) throws IOException {
		List<String[]> content = CSVFile.readCSV(tempstoragesLayout, temstorages_file_fields);
		TempStorage[] storanges = new TempStorage[content.size()];
		for(int i = 0; i != content.size(); ++i) {
			String[] line = content.get(i);
			storanges[i] = new TempStorage(Integer.parseInt(line[1]));
			MutableSE2 pos = new MutableSE2(Double.parseDouble(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]));
			storanges[i].setSiteState(site);
			storanges[i].setPositionAndOrientation(pos);

			System.out.println("Adding " + storanges[i].toString() + " @ (" 
					+ storanges[i].getPositionAndOrientation().getPosition().x + ", "
					+ storanges[i].getPositionAndOrientation().getPosition().y + ")");
		}
		return storanges;
	}

	public UnloadingBay[] genUnloadingBays(ConstructionSiteState site) throws IOException {
		List<String[]> content = CSVFile.readCSV(unloadingbaysLayout, unloadingbays_file_fields);
		UnloadingBay[] bays = new UnloadingBay[content.size()];
		for(int i = 0; i != content.size(); ++i) {
			String[] line = content.get(i);
			bays[i] = new UnloadingBay(Integer.parseInt(line[1]), Boolean.parseBoolean(line[5]));
			MutableSE2 pos = new MutableSE2(Double.parseDouble(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]));
			bays[i].setSiteState(site);
			bays[i].setPositionAndOrientation(pos);

			System.out.println("Adding " + bays[i].toString() + " @ (" 
					+ bays[i].getPositionAndOrientation().getPosition().x + ", "
					+ bays[i].getPositionAndOrientation().getPosition().y + ")");
		}
		return bays;
	}
	
	public Gate[] genSiteEntrances(ConstructionSiteState site) throws IOException {
		List<String[]> content = CSVFile.readCSV(siteentranceLayout, siteentrance_file_fields);
		Gate[] entrances = new Gate[content.size()];
		for(int i = 0; i != content.size(); ++i) {
			String[] line = content.get(i);
			entrances[i] = new Gate(Integer.parseInt(line[1]));
			MutableSE2 pos = new MutableSE2(Double.parseDouble(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]));
			entrances[i].setSiteState(site);
			entrances[i].setPositionAndOrientation(pos);

			System.out.println("Adding " + entrances[i].toString() + " @ (" 
					+ entrances[i].getPositionAndOrientation().getPosition().x + ", "
					+ entrances[i].getPositionAndOrientation().getPosition().y + ")");
		}
		return entrances;
	}
	
}
