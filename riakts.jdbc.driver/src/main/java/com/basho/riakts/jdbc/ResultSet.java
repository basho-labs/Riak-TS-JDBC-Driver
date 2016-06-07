package com.basho.riakts.jdbc;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultSet extends AbstractResultSet {
	
	ResultSet() { 
		rowData = new ArrayList<Object[]>();
		columns = new HashMap<String, String>();
	}

}
