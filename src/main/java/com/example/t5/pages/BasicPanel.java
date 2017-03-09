package com.example.t5.pages;

import com.example.t5.data.Units;
import com.example.t5.entities.BuyerEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.entities.SourceEntity;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Калантаев Александр on 08.03.2017.
 */
public class BasicPanel {

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


    public String getStringFromUnits(Units units){
        if(units == null) return "";
        if(Units.KG == units){
            return "КГ";
        } else if (Units.M == units) {
            return "M";
        }else if (Units.M2 == units) {
            return "м квадратные";
        }else if (Units.M3 == units) {
            return "м кубические";
        }else if (Units.TN == units) {
            return "тн";
        }else if (Units.SHT == units) {
            return "шт";
        }else if (Units.PM == units) {
            return "погонный метр";
        }
        return "";
    }
}
