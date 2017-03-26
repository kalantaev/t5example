package com.example.t5.pages;

import com.example.t5.data.PanelMode;
import com.example.t5.data.Units;
import com.example.t5.entities.BuyerEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.entities.SourceEntity;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Калантаев Александр on 08.03.2017.
 */
public class BasicPanel {

    @Persist
    private PanelMode panelMode;

    public boolean isShowMode(){
        return panelMode == PanelMode.SHOW;
    }

    public boolean isCreatedMode(){
        return panelMode == PanelMode.CREATED;
    }

    public boolean isSipmentMode(){
        return panelMode == PanelMode.SHIPMENT;
    }

    public boolean isTemplateMode(){
        return panelMode == PanelMode.TEMPLATE;
    }

    public void setShowMode(){
        panelMode = PanelMode.SHOW;
    }

    public void setCreatedMode(){
        panelMode = PanelMode.CREATED;
    }

    public  void setShipmentMode(){
        panelMode = PanelMode.SHIPMENT;
    }

    public void setTemplateMode(){
        panelMode = PanelMode.TEMPLATE;
    }

    public class SourceIdSelectModel extends AbstractSelectModel {
        private List<SourceEntity> sources;

        public SourceIdSelectModel(List<SourceEntity> sources) {
            this.sources = sources;
        }

        @Override
        public List<OptionGroupModel> getOptionGroups() {
            return null;
        }

        @Override
        public List<OptionModel> getOptions() {
            List<OptionModel> options = new ArrayList<OptionModel>();
            for (SourceEntity source : sources) {
                options.add(new OptionModelImpl(source.getName(), source.getId()));
            }
            return options;
        }
    }

    public class BuyerIdSelectModel extends AbstractSelectModel {
        private List<BuyerEntity> buyer;

        public BuyerIdSelectModel(List<BuyerEntity> buyer) {
            this.buyer = buyer;
        }

        @Override
        public List<OptionGroupModel> getOptionGroups() {
            return null;
        }

        @Override
        public List<OptionModel> getOptions() {
            List<OptionModel> options = new ArrayList<OptionModel>();
            for (BuyerEntity buyerEntity : buyer) {
                options.add(new OptionModelImpl(buyerEntity.getNameOrganization(), buyerEntity.getId()));
            }
            return options;
        }

    }

    public class ProviderIdSelectModel extends AbstractSelectModel {
        private List<ProviderEntity> provider;

        public ProviderIdSelectModel(List<ProviderEntity> provider) {
            this.provider = provider;
        }

        @Override
        public List<OptionGroupModel> getOptionGroups() {
            return null;
        }

        @Override
        public List<OptionModel> getOptions() {
            List<OptionModel> options = new ArrayList<OptionModel>();
            for (ProviderEntity providerEntity : provider) {
                options.add(new OptionModelImpl(providerEntity.getNameOrganization(), providerEntity.getId()));
            }
            return options;
        }

    }


    public String getStringFromUnits(Units units) {
        if (units == null) return "";
        if (Units.KG == units) {
            return "кг";
        } else if (Units.M == units) {
            return "м";
        } else if (Units.M2 == units) {
            return "м<sup>2</sup>";
        } else if (Units.M3 == units) {
            return "м<sup>3</sup>";
        } else if (Units.TN == units) {
            return "т";
        } else if (Units.SHT == units) {
            return "шт";
        } else if (Units.PM == units) {
            return "пог.м";
        }
        return "";
    }

    public String getStringFromData(Date date){
        if(date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
        return sdf.format(date);
    }
}
