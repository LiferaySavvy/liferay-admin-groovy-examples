import com.liferay.portal.kernel.messaging.Destination
import com.liferay.portal.kernel.messaging.DestinationStatistics
import com.liferay.portal.kernel.messaging.DummyMessageListener
import com.liferay.portal.kernel.messaging.MessageBusUtil

//View Statistics of Message Bus
try {
	String destinationName = "liferaysavvy/parallel-destination";
	String className = DummyMessageListener.class.getName();
	String jobName = "com.liferay.portal.kernel.messaging.DummyMessageListener";
	String groupName = "com.liferay.portal.kernel.messaging.DummyMessageListener";
	//Every minute
	Destination destiNation = MessageBusUtil.getDestination(destinationName);
	if(destiNation){
		out.println(destiNation.getMessageListeners());
		DestinationStatistics destinationStatistics = destiNation.getDestinationStatistics();

		out.println("Destination is registered with Messagebus::${destiNation.isRegistered()}");

		out.println("Sent Message Count    :: ${destinationStatistics.getSentMessageCount()}");
		out.println("Pending Message Count :: ${destinationStatistics.getPendingMessageCount()}");
		out.println("Active Thread Count   :: ${destinationStatistics.getActiveThreadCount()}");
		out.println("Current Thread Count  :: ${destinationStatistics.getCurrentThreadCount()}");
		out.println("Largest Thread Count  :: ${destinationStatistics.getLargestThreadCount()}");
		out.println("Max Thread Pool Size  :: ${destinationStatistics.getMaxThreadPoolSize()}");
		out.println("Min Thread Pool Size  :: ${destinationStatistics.getMinThreadPoolSize()}");
	}else{
		out.println("************Destination not in the Message BUS************");
	}




} catch (Exception e) {
	e.printStackTrace();
}
