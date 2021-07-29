import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactoryUtil;
import com.liferay.portal.kernel.messaging.DummyMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

String destinationName = "liferaysavvy/parallel-destination";
String className = DummyMessageListener.class.getName();
String jobName = "com.liferay.portal.kernel.messaging.DummyMessageListener";
String groupName = "com.liferay.portal.kernel.messaging.DummyMessageListener";
//Every minute
String cronExpression = "0 0/1 * * * ?";
String description = "Every one minute job using Parallel Destination..";
int exceptionsMaxSize = 10;

//Create destination and register listener
try {
	DestinationConfiguration destinationConfig =  DestinationConfiguration.createParallelDestinationConfiguration(destinationName);
	Destination parallelDestination = DestinationFactoryUtil.createDestination(destinationConfig);
	MessageBusUtil.addDestination(parallelDestination);
	String portletId = null;
	ClassLoader classLoader = null;
	if(portletId != null){
		//PortletClassLoaderUtil.setServletContextName(portletId);
		classLoader = PortletClassLoaderUtil.getClassLoader(portletId);
	} else {
		classLoader = PortalClassLoaderUtil.getClassLoader();
	}
	MessageListener messageListener = null;

		messageListener = (MessageListener)classLoader.loadClass(className).newInstance();
		out.printlln("messageListener :: ${messageListener}");
		parallelDestination.register(messageListener);


	//Create schedule job with cron expression.

	Trigger trigger = TriggerFactoryUtil.createTrigger(jobName,groupName,cronExpression);
	Message message = new Message();
	message.put("data","My Data required for job..");
	SchedulerEngineHelperUtil.schedule(trigger, StorageType.PERSISTED, description,destinationName, message, int exceptionsMaxSize)
} catch (Exception e) {
	e.printStackTrace();
}
