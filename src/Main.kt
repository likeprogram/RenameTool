import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.WindowConstants.EXIT_ON_CLOSE

fun main() {
    SwingUtilities.invokeLater {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        val renameEditor = RenameEditor()
        renameEditor.title = "Rename Tool V_$version"
        renameEditor.defaultCloseOperation = EXIT_ON_CLOSE
        renameEditor.setSize(700, 400)
        renameEditor.setLocationRelativeTo(null)
        renameEditor.isResizable = false
        renameEditor.isVisible = true
    }
}