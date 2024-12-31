import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.swing.*


class RenameEditor : JFrame() {
    private lateinit var renameStatus: JTextArea
    private lateinit var fileSuffixName: JTextField
    private lateinit var fileNamePattern: JTextField

    init {
        initFrame()
    }

    private val fileNameSuffix: String
        get() {
            val fileSuffix = fileSuffixName.text.trim()
            if (fileSuffix.isEmpty()) {
                return DEFAULT_FILE_SUFFIX
            }

            return fileSuffix
        }

    private val pattern: String
        get() {
            val pattern = fileNamePattern.text.trim()
            if (pattern.isEmpty()) {
                return DEFAULT_FILE_NAME_PATTERN
            }

            return pattern
        }

    companion object {
        private const val DEFAULT_FILE_SUFFIX = ".bin"
        private const val DEFAULT_FILE_NAME_PATTERN = ".+-(.+_fip\\.bin)"
    }

    private fun initFrame() {
        val contentPane = JPanel()
        this.contentPane.add(contentPane)
        val gridBagLayout = GridBagLayout()
        contentPane.layout = gridBagLayout
        val constraints = GridBagConstraints()
        constraints.ipady = 12

        val fileSuffixNameLabel = JLabel("文件后缀名")
        constraints.gridx = 0
        constraints.gridy = 0
        constraints.anchor = GridBagConstraints.EAST
        constraints.insets = Insets(10, 10, 0, 0)
        gridBagLayout.setConstraints(fileSuffixNameLabel, constraints)
        contentPane.add(fileSuffixNameLabel)

        val fileNamePatternLabel = JLabel("正则表达式")
        constraints.gridx = 0
        constraints.gridy = 1
        gridBagLayout.setConstraints(fileNamePatternLabel, constraints)
        contentPane.add(fileNamePatternLabel)

        fileSuffixName = JTextField(DEFAULT_FILE_SUFFIX)
        constraints.gridx = 1
        constraints.gridy = 0
        constraints.weightx = 1.0
        constraints.insets = Insets(10, 2, 0, 0)
        constraints.fill = GridBagConstraints.HORIZONTAL
        gridBagLayout.setConstraints(fileSuffixName, constraints)
        contentPane.add(fileSuffixName)

        fileNamePattern = JTextField(DEFAULT_FILE_NAME_PATTERN)
        constraints.gridx = 1
        constraints.gridy = 1
        gridBagLayout.setConstraints(fileNamePattern, constraints)
        contentPane.add(fileNamePattern)

        val renameBtn = JButton("批量重命名")
        constraints.anchor = GridBagConstraints.NORTH
        constraints.insets = Insets(10, 10, 0, 0)
        constraints.gridx = 0
        constraints.gridy = 2
        constraints.gridwidth = 2
        gridBagLayout.setConstraints(renameBtn, constraints)
        contentPane.add(renameBtn)
        renameBtn.addActionListener {
            handleRenameFiles()
        }

        renameStatus = JTextArea()
        renameStatus.isEditable = false
        constraints.gridx = 2
        constraints.gridy = 0

        constraints.weightx = 4.0
        constraints.weighty = 1.0
        constraints.fill = GridBagConstraints.BOTH
        constraints.gridwidth = GridBagConstraints.REMAINDER
        constraints.gridheight = GridBagConstraints.REMAINDER
        constraints.insets = Insets(10, 10, 10, 10)
        gridBagLayout.setConstraints(renameStatus, constraints)
        contentPane.add(renameStatus)
    }

    private fun handleRenameFiles() {
        val chooser = JFileChooser()
        chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        val returnVal = chooser.showOpenDialog(this@RenameEditor)
        println("returnVal: $returnVal")
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val file = chooser.selectedFile
            println("Selected file: " + file.absolutePath)
            printStatus("选择的路径：" + file.absolutePath, clear = true)

            val files = file.listFiles { _: File?, name: String -> name.endsWith(fileNameSuffix) }?.also {
                it.sortBy { file ->
                    file.name
                }
            }
            if (!files.isNullOrEmpty()) {
                val pat: Pattern = Pattern.compile(pattern)
                for (f in files) {
                    println("file name: " + f.name)
                    val matcher: Matcher = pat.matcher(f.name)
                    if (matcher.find()) {
//                        printStatus("发现文件：" + f.name)
                        println("matcher group count: ${matcher.groupCount()}")
                        if (matcher.groupCount() >= 1) {
                            val result = f.renameTo(File(f.parentFile, matcher.group(1)))
                            println(result)
                            if (result) {
                                printStatus("${f.name} -> ${matcher.group(1)}")
                            } else {
                                printStatus("${f.name} -> ${matcher.group(1)} failed")
                            }
                        } else {
                            printStatus("输入的正则表达式不包含目标文件名分组")
                        }
                    }
                }
                printStatus("Done!!!")
            } else {
                printStatus("没有满足条件的文件!!!")
            }
        }
    }

    private fun printStatus(status: String, clear: Boolean = false) {
        if (clear) {
            renameStatus.text = "$status\n\n"
        } else {
            renameStatus.append("$status\n\n")
        }
    }
}