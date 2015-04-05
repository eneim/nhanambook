package im.ene.lab.nnbook.models;

import java.util.ArrayList;
import java.util.List;

public class NNCategory {

	public NNCategoryHeader parentHead;

	public List<NNCategoryHeader> childHeads;

	public List<NNBookShelf> parentBookShelves;

	public NNCategory() {
		parentHead = new NNCategoryHeader();
		childHeads = new ArrayList<NNCategoryHeader>();
	}

	public NNCategoryHeader getParentHead() {
		return parentHead;
	}

	public void setParentHead(NNCategoryHeader parentHead) {
		this.parentHead = parentHead;
	}

	public List<NNCategoryHeader> getChildHeads() {
		return childHeads;
	}

	public void setChildHeads(List<NNCategoryHeader> childHeads) {
		this.childHeads = childHeads;
	}

	public List<NNBookShelf> getParentBookShelves() {
		return parentBookShelves;
	}

	public void setParentBookShelves(List<NNBookShelf> parentBookShelves) {
		this.parentBookShelves = parentBookShelves;
	}

}
