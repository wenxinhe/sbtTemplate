package patterns

import java.io.{BufferedWriter, File, FileWriter}

trait WriteSupport {
  def withWriter[A](path: Path)(f: BufferedWriter => A) {
    var writer: BufferedWriter = null
    try {
      writer = new BufferedWriter(new FileWriter(path.value))
      f(writer)
    } finally {
      if (writer != null)
        writer.close()
    }
  }
}
