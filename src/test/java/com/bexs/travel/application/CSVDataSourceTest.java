package com.bexs.travel.application;

import com.bexs.travel.application.infrastructure.datasource.CSVDataSource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class CSVDataSourceTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    CSVDataSource csvDataSource;

    @Test
    public void shouldReturnAFile() throws IOException {
        File file = folder.newFile();

        Mockito.when(this.csvDataSource.getDataSource()).thenReturn(file);

        Assert.assertTrue(this.csvDataSource.getDataSource().isFile());
    }

    @Test
    public void shouldNotReturnAFile() throws IOException {
        File file = folder.newFolder();

        Mockito.when(this.csvDataSource.getDataSource()).thenReturn(file);

        Assert.assertFalse(this.csvDataSource.getDataSource().isFile());
    }
}
