/**
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.reports.openoffice.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.jboss.seam.reports.ReportException;

public class JODConverterFacade {

    private OfficeManager officeManager;

    public JODConverterFacade() {

    }

    public JODConverterFacade(OfficeManager officeManager) {
        this.officeManager = officeManager;
    }

    public void start() {
        officeManager.start();
    }

    public void stop() {
        officeManager.stop();
    }

    private void inputStreamToFile(InputStream input, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = input.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

        } catch (IOException e) {
            throw new ReportException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new ReportException(e);
                }
            }
        }
    }

    private void fileToOutputStream(File file, OutputStream output) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = fis.read(buf)) > 0) {
                output.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new ReportException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new ReportException(e);
                }
            }
        }

    }

    public void convert(InputStream input, OutputStream output) {

        File fin = null, fout = null;

        try {
            fin = File.createTempFile(UUID.randomUUID().toString(), ".pdf");
            fout = File.createTempFile(UUID.randomUUID().toString(), ".pdf");

            inputStreamToFile(input, fin);
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            converter.convert(fin, fout);
            fileToOutputStream(fout, output);
        } catch (IOException e) {
            throw new ReportException(e);
        } finally {
            if (fin != null) {
                fin.delete();
            }
            if (fout != null) {
                fout.delete();
            }
        }

    }

    public OfficeManager getOfficeManager() {
        return officeManager;
    }

}
