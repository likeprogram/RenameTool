import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater {
        val frame = createRootFrame()

        val tabbedPane = createTabbedPane()
        frame.contentPane.add(tabbedPane)

        val gridBagLayout = createGridBagLayout()
        tabbedPane.addTab("GridBagLayout", gridBagLayout)

        val box = createBox()
        tabbedPane.addTab("Box", box)

        frame.isVisible = true
        createGridBagLayout()
    }
}

private fun createRootFrame(): JFrame {
    val frame = JFrame()
    frame.setSize(600, 400)
    frame.setLocationRelativeTo(null)
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    return frame
}

private fun createTabbedPane(): JTabbedPane {
    val tabbedPane = JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT)
    return tabbedPane
}

private fun createBox(): Box {
    val box = Box.createHorizontalBox()

    repeat(3) {
        val btn = JButton("${it + 1}")
        box.add(btn)
    }
//    box.add(Box.createHorizontalGlue())
    Box.createHorizontalStrut(30)
    val vBox = Box.createVerticalBox()
    box.add(vBox)

    repeat(4) {
        val btn = JButton("${it + 1}")
        vBox.add(btn)
    }

    return box
}

private fun createGridBagLayout(): JPanel {
    val panel = JPanel()
    val gridBagLayout = GridBagLayout()
    val gridBagConstraints = GridBagConstraints()
    panel.layout = gridBagLayout

    gridBagConstraints.fill = GridBagConstraints.BOTH
    gridBagConstraints.ipady = 12
    gridBagConstraints.insets = Insets(15, 15, 15, 15)
    gridBagConstraints.weightx = 1.0
    makeButton("Button1", panel, gridBagLayout, gridBagConstraints)
    makeButton("Button2", panel, gridBagLayout, gridBagConstraints)
    makeButton("Button3", panel, gridBagLayout, gridBagConstraints)

    gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER // end row
    makeButton("Button4", panel, gridBagLayout, gridBagConstraints)

    gridBagConstraints.weightx = 0.0
    gridBagConstraints.gridwidth = 1
    makeButton("Button5", panel, gridBagLayout, gridBagConstraints)

    gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE //next-to-last in row
    makeButton("Button6", panel, gridBagLayout, gridBagConstraints)

    return panel
}

private fun makeButton(
    name: String,
    panel: JPanel,
    gridBagLayout: GridBagLayout,
    gridBagConstraints: GridBagConstraints
) {
    val button = JButton(name)
    gridBagLayout.setConstraints(button, gridBagConstraints)
    panel.add(button)
}