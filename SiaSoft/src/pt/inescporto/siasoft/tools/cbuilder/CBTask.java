package pt.inescporto.siasoft.tools.cbuilder;

import java.io.*;
import java.util.*;
/*
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.pdfbox.searchengine.lucene.*;
import pt.inescporto.siasoft.tools.*;
import pt.inescporto.siasoft.tools.test.*;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class CBTask {/*
  private MsgPublisher poster = null;
  private String baseDir = null;
  private String catalogName = null;

  private int lengthOfTask = 1;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  public CBTask(MsgPublisher poster) {
    this.poster = poster;
  }

  /**
   * Called from ProgressBarDemo to start the task.

  public void go() {
    final SwingWorker worker = new SwingWorker() {
      public Object construct() {
        current = 0;
        done = false;
        canceled = false;
        statMessage = null;
        return new CBTaskImpl(poster, baseDir, catalogName);
      }
    };
    worker.start();
  }

  /**
   * Called from ProgressBarDemo to find out how much work needs
   * to be done.

  public int getLengthOfTask() {
    return lengthOfTask;
  }

  /**
   * Called from ProgressBarDemo to find out how much has been done.

  public int getCurrent() {
    return current;
  }

  public void stop() {
    canceled = true;
    statMessage = null;
  }

  /**
   * Called from ProgressBarDemo to find out if the task has completed.

  public boolean isDone() {
    return done;
  }

  /**
   * The actual long running task.  This runs in a SwingWorker thread.
   *//*
  public class CBTaskImpl {
    private MsgPublisher poster;
    public CBTaskImpl(MsgPublisher poster, String baseDir, String indexName) {
      this.poster = poster;

      File indexFile = new File(indexName);

      if (indexFile.exists()) {
        indexFile.delete();
        poster.setProgress("A directoria index já existe. Removida!", 0);
      }

      final File docDir = new File(baseDir);
      if (!docDir.exists() || !docDir.canRead()) {
        poster.setProgress("A directoria " + baseDir + " não existe! ", 0);
        done = true;
        current = lengthOfTask;
        poster.setProgress("ERRO!", current);
        return;
      }

      Date start = new Date();

      try {
        IndexWriter writer = new IndexWriter(indexFile, new StandardAnalyzer(), true);
        poster.setProgress("A Indexar a directoria '" + baseDir + "'...", 0);
        indexDocs(writer, docDir);
        poster.setProgress("Optimização...", 0);
        writer.optimize();
        writer.close();

        Date end = new Date();
        poster.setProgress("Tempo " + ((end.getTime() - start.getTime())/1000 + " s"), 0);

      }
      catch (IOException e) {
        System.out.println(" caught a " + e.getClass() +
                           "\n with message: " + e.getMessage());
      }

      done = true;
      current = lengthOfTask;
      poster.setProgress("Fim.", current);
    }

    private void indexDocs(IndexWriter writer, File file) throws IOException {
      // do not try to index files that cannot be read
      if (file.canRead()) {
        if (file.isDirectory()) {
          String[] files = file.list();
          // an IO error could occur
          if (files != null) {
            for (int i = 0; i < files.length; i++) {
              indexDocs(writer, new File(file, files[i]));
            }
          }
        }
        else {
          poster.setProgress("A processar documento <" + file.getName() + ">.", current);
          if (file.getName().endsWith(".pdf")) {
            Document luceneDocument = null;
            try {
              luceneDocument = LucenePDFDocument.getDocument(file);
            }
            catch (IOException ex) {
//              poster.setProgress(ex.getMessage(), 0);
	      poster.setProgress("Documento " + file + " ENCRIPTADO!!!", 0);
              return;
            }

            try {
              System.out.println("adding " + file);
              writer.addDocument(luceneDocument);
            }
            // at least on windows, some temporary files raise this exception with an "access denied" message
            // checking if the file can be read doesn't help
            catch (FileNotFoundException fnfe) {
              ;
            }
          }
          else {
            poster.setProgress("O documento " + file + " não está no formato PDF.", 0);
          }
        }
      }
    }
  }

  public void setBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }*/
}
