package pt.inescporto.template.client.event;

import java.io.Serializable;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class EventSynchronizer implements Serializable {
  private static EventSynchronizer instance = null;
  private Map<String, Vector<EventSynchronizerWatcher>> eventListeners = Collections.synchronizedMap(new Hashtable<String, Vector<EventSynchronizerWatcher>>());
  private boolean debug = false;

  private EventSynchronizer() {
    instance = this;
  }

  public static EventSynchronizer getInstance() {
    if (instance == null)
      instance = new EventSynchronizer();
    return instance;
  }

  public void addEventSynchronizerWatcher(EventSynchronizerWatcher watcher, String subject) {
    synchronized (subject) {
      if (!eventListeners.containsKey(subject)) {
	Vector<EventSynchronizerWatcher> watcherList = new Vector<EventSynchronizerWatcher>();
        eventListeners.put(subject, watcherList);
      }

      if (!eventListeners.get(subject).contains(watcher)) {
	eventListeners.get(subject).add(watcher);
	if (debug)
	  System.out.println("[EventSynchronizer] |addEventSynchronizerWatcher| Watcher is <" + watcher + ">, Subject is <" + subject + ">");
      }
    }
  }

  public void removeEventSynchronizerWatcher(EventSynchronizerWatcher watcher, String subject) {
    synchronized (subject) {
      if (eventListeners.containsKey(subject)) {
	eventListeners.get(subject).remove(watcher);
        if (debug)
          System.out.println("[EventSynchronizer] |removeEventSynchronizerWatcher| Watcher is <" + watcher + ">, Subject is <" + subject + ">");
      }
    }
  }

  public void triggerEvent(String subject) {
    synchronized (subject) {
      if (eventListeners.containsKey(subject)) {
        Vector<EventSynchronizerWatcher> watcher2remove = new Vector<EventSynchronizerWatcher>();
        Vector<EventSynchronizerWatcher> watcherList = eventListeners.get(subject);
        for (EventSynchronizerWatcher watcher : watcherList) {
	  try {
	    watcher.eventSynchronizerTriggered(subject);
            if (debug)
              System.out.println("[EventSynchronizer] |triggerEvent| Watcher is <" + watcher + ">, Subject is <" + subject + ">");
	  }
	  catch (Exception ex) {
            watcher2remove.add(watcher);
	  }
        }
        for (EventSynchronizerWatcher watcher : watcher2remove) {
          eventListeners.get(subject).remove(watcher);
        }
      }
    }
  }
}
