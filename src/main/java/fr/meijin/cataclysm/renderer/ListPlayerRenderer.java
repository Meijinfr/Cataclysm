package fr.meijin.cataclysm.renderer;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import fr.meijin.cataclysm.converter.FactionConverter;
import fr.meijin.cataclysm.model.Player;

public class ListPlayerRenderer implements ListitemRenderer<Player> {
	
	private static final FactionConverter FACTION_CONVERTER = new FactionConverter();
	
	@Override
	public void render(Listitem item, Player p, int i) throws Exception {
		createLabelCell(String.valueOf(p.id)).setParent(item);
		createLabelCell(p.firstName).setParent(item);
		createLabelCell(p.lastName).setParent(item);
		createLabelCell(p.nickname).setParent(item);
		createIdCell(p.faction).setParent(item);

		createListcellButtons(p).setParent(item);
	}

	private Component createIdCell(String id) {
		if (StringUtils.isNotBlank(id)){
			Listcell cell = new Listcell();
			Hlayout h = new Hlayout();
			Image image = new Image();
			image.setSrc((String) FACTION_CONVERTER.coerceToUi(id, image));
			Label label = new Label();
			label.setValue((String) FACTION_CONVERTER.coerceToUi(id, label));
			h.setSpacing("10px");
			Div div = new Div();
			div.setStyle("margin-top : 7px;");
			
			label.setParent(div);
			image.setParent(h);
			div.setParent(h);
			h.setParent(cell);
			return cell;
		}
		
		return new Listcell();
	}

	private Listcell createLabelCell(String label) {
		if (label != null)
			return new Listcell(label);

		return new Listcell();
	}
	
	private Listcell createListcellButtons(final Player p){
		Listcell listcell = new Listcell();
		
		Button editButton = new Button("Modifier");
		editButton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event e) throws Exception {
				Events.postEvent("onEditPlayer", (Component) e.getPage().getFellow("divIndex"),  p);
			}
			
			
		});
		editButton.setStyle("margin-right : 10px;");
		editButton.setMold("trendy");
		editButton.setParent(listcell);
		
		Button deleteButton = new Button("Supprimer");
		deleteButton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event e) throws Exception {
				Events.postEvent("onDeletePlayer", (Component) e.getPage().getFellow("divIndex"),  p);
			}
			
		});
		deleteButton.setMold("trendy");
		deleteButton.setParent(listcell);
		
		return listcell;
	}

}