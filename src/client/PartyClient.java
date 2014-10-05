package client;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import person.RemotePerson;
import person.RemotePersonImpl;
import registry.LookupRegistry;

public class PartyClient {

	private static LookupRegistry lookupRegistry;

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Please specify server host name");
			return;
		}
		String hostName = args[0];

		// Create a registry to lookup remote objects
		lookupRegistry = new LookupRegistry(hostName);

		// Generate a few people living on this device
		System.out.println("Creating local people");
		RemotePerson sally = new RemotePersonImpl("sally");
		RemotePerson ben = new RemotePersonImpl("ben");
		RemotePerson jim = new RemotePersonImpl("jim");
		RemotePerson localRobert = new RemotePersonImpl("robert");

		// Lookup people living on the server
		System.out.println("Getting remote people");
		RemotePerson elain;
		RemotePerson remoteRobert;
		try {
			elain = (RemotePerson) lookupRegistry.lookup("elain");
			remoteRobert = (RemotePerson) lookupRegistry.lookup("robert");

		} catch (RemoteException e1) {
			System.out.println("Ooops unable to find remote people");
			return;
		}

		// Lookup non-existent person
		RemotePerson theInvisibleMan = null;
		try {
			theInvisibleMan = (RemotePerson) lookupRegistry.lookup("theInvisibleMan");
			System.out.println("Got theInvisibleMan " + theInvisibleMan);
		} catch (RemoteException e1) {
			System.out.println("Good - unable to find non-existent remote person");
		}

		// Fill the party
		List<RemotePerson> party = new LinkedList<RemotePerson>();
		party.add(sally);
		party.add(ben);
		party.add(jim);
		party.add(localRobert);
		party.add(elain);
		party.add(remoteRobert);

		// Get the names of everyone at the party
		System.out.println("These people are at the party:");
		for (RemotePerson person : party) {
			try {
				System.out.print(person.getName() + " ");
			} catch (RemoteException e) {
				System.out.println("Oops someone couldn't make it");
				return;
			}
		}
		System.out.println();

		// Check if anyone is on the party list twice (the client shouldn't care
		// where the objects live
		try {
			System.out.println("Was Robert was double counted (true)? "
					+ localRobert.samePerson(remoteRobert));
			System.out
			.println("To check again, was Robert was double counted (true)? "
					+ remoteRobert.samePerson(localRobert));
			System.out.println("Are Elain and Robert the same person (false)? "
					+ remoteRobert.samePerson(elain));
		} catch (RemoteException e) {
			System.out.println("Oops someone went missing");
			return;
		}

		// Check that an exception is returned for bad people
		try {
			System.out.println("Oops we didn't get an error?"
					+ remoteRobert.samePerson(theInvisibleMan));
		} catch (Exception e) {
			System.out.println("Good we got an exception " + e.getCause() );
		}
	}

}
