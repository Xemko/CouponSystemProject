package b.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import e.Exceptions.CouponSystemException;

public class ConnectionPool {
	
	public static final int MAX_CONNECTIONS = 10;
	
	private Set<Connection> connections = new HashSet<>();
	private Set<Connection> connectionsBackUp = new HashSet<>();
	
	private static ConnectionPool instance;
	
	private String url = "jdbc:derby://localhost:1527/cs;create=true;user=project;password=12345";
	
	// Constructor for the singleton Connection Pool with 10 connections + creating backup set for a convenient [close all connection] method
	private ConnectionPool() throws CouponSystemException{
		
		for (int i = 0; i < MAX_CONNECTIONS; i++){
			
			try {
				Connection con = DriverManager.getConnection(url);
				connections.add(con);
				connectionsBackUp.add(con);
			} catch (SQLException e) {
				throw new CouponSystemException("SQL error - Initialization of Connection Pool has failed!", e);
			}
		}
	}
	
	/** Returns a reference to this singleton Connection Pool 
	 * @throws CouponSystemException */
	public static ConnectionPool getInstance() throws CouponSystemException{
		
		if (instance == null){
				instance = new ConnectionPool();
		}

		return instance;
	}
	
	/** Gets a connection from the Connection Pool 
	 * @throws CouponSystemException */
	public synchronized Connection getConnection() throws CouponSystemException{
		
		while (connections.isEmpty()){
			try{
				this.wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Error occured during Connection Pool verifying empty status!",e);
			}
		}
		
		try {
			Iterator<Connection> it = connections.iterator();
			Connection con = it.next();
			it.remove();
			return con;
		} catch (Exception e) {
			throw new CouponSystemException("Error occured while getting a connection from Connection Pool!",e);
		} 
						
	}
	
	/** Returns this connection to the Connection Pool 
	 * @throws CouponSystemException */
	public synchronized void returnConnection(Connection con) throws CouponSystemException{
	
		try {
			connections.add(con);
			this.notify();
		} catch (Exception e) {
			throw new CouponSystemException("Error occured while returning a connection to Connection Pool!",e);
		}
	}
	
	/** Closes all connections via backup Connection Pool 
	 * @throws CouponSystemException */
	public void CloseAllConnections() throws CouponSystemException{
		
		for (Connection con : connectionsBackUp){
			if (con != null){
				try {
					con.close();
				} catch (SQLException e) {
					throw new CouponSystemException("SQL error - All connection close operation has failed!",e);
				}
			}
		}
	}

	
}
