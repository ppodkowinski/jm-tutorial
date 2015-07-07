package com.acme.craft.fixme.cleancode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SlideshowService {

	private ResourceHolderRepository resourceHolderRepository;
	private ResourceHolderResourceRepository resourceHolderResourceRepository;
	private ResourceHolderScheduleRepository rsrcHldrSchdleRpstry;
	private scheduleRepository ScheduleServiceImplSimple;

	public SlideshowData generateTimelineData(String resourceHolderId) throws Exception {
		ResourceHolder resourceHolder = resourceHolderRepository.findOne(resourceHolderId);
		exceptionNullResourceHolder(resourceHolder);
		Resource resource = getIdResource();
		Asset defaultAsset = defaultAsset(resource);
		Slideshow timeline = new Slideshow();
		setTimeline(defaultAsset, timeline);
		ResourceSchedule schedule = ScheduleServiceImplSimple.findOne(resource.getScheduleId());
		nullExceptionSchedule(schedule);

		ArrayList<ResourceSchedule> getResourceSchedules = schedule.getResourceSchedules();
		int sizeOfResourceSchedules = getResourceSchedules.size();
		if (sizeOfResourceSchedules == 0) {
			throw new Exception("", null);
		}

		HashMap<String, Asset> assets = setRecourceIds(getResourceSchedules);
		List<SlideshowInterval> timelineIntervalList = new ArrayList<>();
		int slide = 0;
		Calendar calendar = GregorianCalendar.getInstance();
		long timeInMillis = calendar.getTimeInMillis();
		slide = resourceSchedules(defaultAsset, getResourceSchedules, assets, timelineIntervalList, slide,
				timeInMillis);
		if (sizeOfResourceSchedules > 0) {
			ResourceSchedule sizeOfResourceSchedule = getResourceSchedules.get(sizeOfResourceSchedules - 1);
			if (timeInMillis > sizeOfResourceSchedule.getEndTime()) {
				slide = 0;
			}
			timelineIntervalList.add(
					resourceScheduleToDate(sizeOfResourceSchedule, assets.get(sizeOfResourceSchedule.getResourceId())));
		}
		timeline.setDate(timelineIntervalList);
		return new SlideshowData(timeline, slide);
	}

	private HashMap<String, Asset> setRecourceIds(ArrayList<ResourceSchedule> getResourceSchedules) {
		Set<String> resourceIds = new HashSet<>();
		for (ResourceSchedule item : getResourceSchedules) {
			resourceIds.add(item.getResourceId());
		}
		Iterable<Resource> resourcesSet = resourceHolderResourceRepository.findAll(resourceIds);
		HashMap<String, Asset> assets = resourcesToAssetMap(resourcesSet);
		return assets;
	}

	private Asset defaultAsset(Resource resource) {
		Asset defaultAsset = null;
		if (resource != null) {
			defaultAsset = resourceToAsset(resource);
		}
		return defaultAsset;
	}

	private Resource getIdResource() {
		Resource resource = null;
		Object GetIdResource = resource.getContentId();
		if (GetIdResource != null) {
			resource = resourceHolderResourceRepository.findOne(GetIdResource);
		}
		return resource;
	}

	private void exceptionNullResourceHolder(ResourceHolder resourceHolder) throws Exception {
		if (resourceHolder == null) {
			throw new Exception("some error");
		}
	}

	private void setTimeline(Asset defaultAsset, Slideshow timeline) {
		timeline.setHeadline("Slideshow");
		timeline.setType("default");
		if (defaultAsset != null) {
			timeline.setText("This is your default Slideshow content");
			timeline.setAsset(defaultAsset);
		} else {
			timeline.setText("You dont have default content for Slideshow");

		}
	}

	private void nullExceptionSchedule(ResourceSchedule schedule) {
		if (schedule == null) {
			try {
				throw new Exception("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private int resourceSchedules(Asset defaultAsset, ArrayList<ResourceSchedule> getResourceSchedules,
			HashMap<String, Asset> assets, List<SlideshowInterval> timelineIntervalList, int slide, long timeInMillis) {
		int sizeSchedules = getResourceSchedules.size();
		for (int i = 0; i < sizeSchedules - 1; ++i) {
			ResourceSchedule GetResourceSchedules = getResourceSchedules.get(i);
			if (timeInMillis > GetResourceSchedules.getStartTime()) {
				++slide;
			}
			timelineIntervalList.add(resourceScheduleToDate(GetResourceSchedules, assets.get(GetResourceSchedules.getResourceId())));
			if (defaultAsset != null) {
				long getEndTime = GetResourceSchedules.getEndTime();
				if (getEndTime != getResourceSchedules.get(i + 1).getStartTime()) {
					if (getEndTime < timeInMillis) {
						++slide;
					}
					timelineIntervalList.add(defaultDate(getEndTime, getResourceSchedules.get(i + 1).getStartTime(), defaultAsset));
				}
			}
		}
		return slide;
	}

	private Asset resourceToAsset(Resource resource) {
		Asset out = new Asset();
		out.setMedia(resource.getId());
		out.setCredit(resource.getContentType());
		out.setCaption(resource.getName());
		out.setThumbnail(resource.getId());
		return out;
	}

	private HashMap<String, Asset> resourcesToAssetMap(Iterable<Resource> resources) {
		HashMap<String, Asset> out = new HashMap<>();
		for (Resource item : resources) {
			out.put(item.getId(), resourceToAsset(item));
		}
		return out;
	}

	private SlideshowInterval resourceScheduleToDate(ResourceSchedule schedule, Asset asset) {
		SlideshowInterval out = new SlideshowInterval();
		out.setStartDate(timestampToTimelineDate(schedule.getStartTime()));
		out.setEndDate(timestampToTimelineDate(schedule.getEndTime()));
		out.setHeadline(schedule.getName());
		out.setAsset(asset);
		return out;
	}

	private SlideshowInterval defaultDate(long start, long end, Asset asset) {
		SlideshowInterval out = new SlideshowInterval();
		out.setStartDate(timestampToTimelineDate(start));
		out.setEndDate(timestampToTimelineDate(end));
		out.setHeadline("Default Content");
		out.setAsset(asset);
		return out;
	}

	private String timestampToTimelineDate(long timestamp) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(calendar.get(Calendar.YEAR)).append(",").append(calendar.get(Calendar.MONTH) + 1)
				.append(",").append(calendar.get(Calendar.DAY_OF_MONTH)).append(",")
				.append(calendar.get(Calendar.HOUR_OF_DAY)).append(",").append(calendar.get(Calendar.MINUTE));
		return stringBuilder.toString();
	}
}
