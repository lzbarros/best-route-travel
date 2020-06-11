package com.bexs.travel.application.infrastructure.datasource;

import javax.inject.Named;
import java.io.File;

@Named("csvDataSource")
public class CSVDataSource implements IDataSource<File> {
    @Override
    public File getDataSource() {
        return new File(System.getProperty("user.dir"), "input-routes.csv");
    }
}
