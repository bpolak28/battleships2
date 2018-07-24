package pl.bpol.form;

import java.util.ArrayList;
import java.util.List;

public class FieldsForm {

    private List<String> positions = new ArrayList<>();


    public FieldsForm() {
    }


	public FieldsForm(List<String> positions) {
		this.positions = positions;
	}


	public List<String> getPositions() {
		return positions;
	}


	public void setPositions(List<String> positions) {
		this.positions = positions;
	}


	@Override
	public String toString() {
		return "FieldsForm [positions=" + positions + "]";
	}
    
    
    
    
}
