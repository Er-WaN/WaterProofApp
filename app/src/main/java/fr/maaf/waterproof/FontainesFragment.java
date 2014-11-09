package fr.maaf.waterproof;

import fr.maaf.waterproof.dao.SeanceDAO;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FontainesFragment extends Fragment {
	
	public FontainesFragment(){}
	
	ListView listeViewSeances;
		
	View inflatedView = null;
	
	private SeanceDAO seanceDAO;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		this.inflatedView = inflater.inflate(R.layout.fragment_historique, container, false);
		
		/*listeViewSeances = (ListView) inflatedView.findViewById(R.id.listeSeances);
		
	    List<String> exemple = new ArrayList<String>();
	    exemple.add("Item 1");
	    exemple.add("Item 2");
	    exemple.add("Item 3");
	        
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, exemple);
	    listeViewSeances.setAdapter(adapter);*/
 
        View rootView = inflater.inflate(R.layout.fragment_historique, container, false);
         
        return rootView;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onActivityCreated(Bundle savedInstanceState) {
		
	    super.onActivityCreated(savedInstanceState);
	    
	    seanceDAO = new SeanceDAO(this.getActivity().getApplicationContext());
		seanceDAO.open();
	    
        
        ListView listView = (ListView) getActivity().findViewById(R.id.listeSeances);  
        listView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, seanceDAO.getListeSeanceString()));
	    
	   
	  }
}
