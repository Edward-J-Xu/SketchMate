package wb.frontend

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import javafx.util.Callback
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.codebot.shared.VerifyCredential
import wb.backend.Blogout
import wb.backend.logout
import wb.helper.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.system.exitProcess

class TopMenu(stage: Stage) : MenuBar() {

    // Menu choices
    private val fileMenu = Menu("File")

    //    private val editMenu = Menu("Edit")
    private val helpMenu = Menu("Help")
    private val accountMenu = Menu("Account")
    private val themeMenu = Menu("Theme")

    // File sub-menu
    private val fileNew = MenuItem("New Remote Board (CMD+N)")
    private val fileOpen = MenuItem("Open Remote Board")
    private val fileLocal = MenuItem("Return to Local Board")
    private val fileSave = MenuItem("Save (CMD+S)")
    private val fileLoad = MenuItem("Load (CMD+O)")
    private val fileExPNG = MenuItem("Export as PNG (CMD+I)")
    private val fileExPDF = MenuItem("Export as PDF (CMD+P)")
    private val fileQuit = MenuItem("Quit")

    // Edit sub-menu
    private val editUndo = MenuItem("Undo")
    private val editRedo = MenuItem("Redo")
    private val editCut = MenuItem("Cut Item")
    private val editCopy = MenuItem("Copy Item")
    private val editPaste = MenuItem("Paste Item")

    // Help sub-menu
    private val helpAbout = MenuItem("About")

    // Account sub-menu
    private val accountLogOut = MenuItem("Log Out")
    private val accountChangeP = MenuItem("Change Password")

    // Theme sub-menu
    private val lightTheme = MenuItem("light")
    private val darkTheme = MenuItem("dark")
    val lightStyle = "-fx-text-fill: crimson  ; -fx-font-size: 14px;"

    init {
        fileMenu.items.addAll(fileNew, fileOpen, fileSave, fileLoad, fileExPNG, fileExPDF, fileQuit, fileLocal)

        helpMenu.items.addAll((helpAbout))

        accountMenu.items.add(accountChangeP)
        themeMenu.items.addAll(lightTheme, darkTheme)
        darkTheme.setOnAction {
            setTheme("dark")
            wb.toolMenu.setTheme("dark")
        }
        lightTheme.setOnAction {
            setTheme("light")
            wb.toolMenu.setTheme("light")
        }

        // Add ActionListener to the MenuItem
        fileQuit.setOnAction {
            // Handle the action event
            exitProcess(0) // Exit the app with status code 0 (successful termination)
        }

        registerControllers(stage)
        fileControllers(stage)
        fileExPNG.setOnAction { exportPNG(stage) }
        fileExPDF.setOnAction { exportPDF(stage) }

        helpAbout.setOnAction { showCopyright() }


        menus.addAll(fileMenu, helpMenu, accountMenu, themeMenu)

        setTheme("light")

        val autoSync: Timer = Timer()
        autoSync.scheduleAtFixedRate(timerTask() {
            Platform.runLater {
                load()
            }
        }, 1000, 1000)
    }

    private fun setTheme(theme: String) {
        if (theme == "light") {
            style = "-fx-background-color: lightblue;"
            fileMenu.style = "-fx-background-color: lightcyan  ;"

            helpMenu.style = "-fx-background-color: lightcyan  ;"
            accountMenu.style = "-fx-background-color: lightcyan  ;"
            themeMenu.style = "-fx-background-color: lightcyan  ;"
            fileNew.style = lightStyle
            fileOpen.style = lightStyle
            fileLocal.style = lightStyle
            fileSave.style = lightStyle
            fileLoad.style = lightStyle
            fileExPNG.style = lightStyle
            fileExPDF.style = lightStyle
            fileQuit.style = lightStyle

            editUndo.style = lightStyle
            editRedo.style = lightStyle
            editCut.style = lightStyle
            editCopy.style = lightStyle
            editPaste.style = lightStyle

            helpAbout.style = lightStyle

            accountLogOut.style = lightStyle
            accountChangeP.style = lightStyle

            lightTheme.style = lightStyle
            darkTheme.style = lightStyle
        } else {
            style = "-fx-background-color: darkslategray;"
            fileMenu.style = "-fx-text-fill: aliceblue   ;-fx-background-color: indigo  ;"

            helpMenu.style = "-fx-text-fill: aliceblue   ;-fx-background-color: indigo  ;"
            accountMenu.style = "-fx-text-fill: aliceblue   ;-fx-background-color: indigo  ;"
            themeMenu.style = "-fx-text-fill: aliceblue   ;-fx-background-color: indigo  ;"
        }
    }


    private fun fileControllers(
        stage: Stage
    ) {
        fileNew.setOnAction {
            val inputDialog = TextInputDialog()
            inputDialog.headerText = "Enter New Board name:"
            val result = inputDialog.showAndWait()
            result.ifPresent { fileName ->
                try {
                    wb.rootcanvas.children.clear()
                    save()
                    var jsonname = "${wb.backend.username}_${wb.backend.boardname}_data.json"
                    val file = File(jsonname)
                    val reader = BufferedReader(FileReader(file))
                    var data = reader.readText()

                    data = Json.encodeToString(data).replace("\\", "").replace("\"", "\\\"")

                    reader.close()
                    println(wb.backend.createBoard(fileName, data))
                    println(wb.backend.Blogin(fileName, ""))
                    wb.backend.boardname = fileName
                    updateTitle(stage)
                } catch (e: Exception) {
                    showWarnDialog("Error", e.toString())
                }
            }
        }

        fileLocal.setOnAction {
            showWarnDialog("message", Blogout())
            wb.backend.boardname = ""
            wb.backend.boardname = ""
            wb.backend.json = ""
            wb.backend.cookieValueB = ""
            updateTitle(stage)
        }

        fileSave.setOnAction {
            save()
        }

        fileLoad.setOnAction {
            load()
        }

        fileOpen.setOnAction {
            var listOfBoards = wb.backend.getBoards()
            val optionsList = if (listOfBoards.isNullOrEmpty()) {
                listOf("N/A")
            } else {
                listOfBoards.map { it.second }
            }
            val choiceDialog = ChoiceDialog<String>(optionsList.firstOrNull(), optionsList)
            choiceDialog.headerText = "Select which remote board:"
            choiceDialog.contentText = "Board options:"
            val result = choiceDialog.showAndWait()
            result.ifPresent { location ->
                val selectedPair = listOfBoards.find { it.second == location }
                if (selectedPair != null) {
                    wb.backend.boardname = location
                    wb.backend.boardId = selectedPair.first
                    println(wb.backend.Blogin(location, ""))
                    wb.rootcanvas.children.clear()
                    load()
                    updateTitle(stage)
                }
            }
        }
    }

    private fun registerControllers(stage: Stage) {

        accountChangeP.setOnAction {

            val dialog: Dialog<VerifyCredential> = Dialog()
            dialog.title = "Change Password"
            dialog.headerText = "Please enter your new Password:"
            dialog.isResizable = false

            val label1 = Label("password: ")
            val label2 = Label("repeat password: ")
            val password = PasswordField()
            val verifyPassword = PasswordField()

            val buttonTypeOk = ButtonType("Sign In", ButtonBar.ButtonData.OK_DONE)

            val grid = GridPane()
            grid.add(label1, 1, 1)
            grid.add(password, 2, 1)
            grid.add(label2, 1, 2)
            grid.add(verifyPassword, 2, 2)
            dialog.dialogPane.content = grid

            dialog.dialogPane.buttonTypes.add(buttonTypeOk)

            dialog.x = 400.0
            dialog.y = 400.0

            dialog.resultConverter = Callback<ButtonType?, VerifyCredential?> {
                if (it == buttonTypeOk) VerifyCredential("", password.text, verifyPassword.text) else null
            }

            // 'x' functionality.
            dialog.setOnCloseRequest {
                dialog.hide()
            }

            val result = dialog.showAndWait()
            println("${result.get().username} ${result.get().password}")

            if (result.isPresent) {
                // now we check if two password is the same
                if (result.get().password != result.get().verifyPassword) {
                    showWarnDialog("Password incorrect!", "Password doesn't match, please try again!")
                } else if (result.get().password == "") {
                    showWarnDialog("Unspecified Password!", "Please enter password!")
                } else {
                    try {
                        // todo: add some output to this
                        println(wb.backend.updateUser(wb.backend.username, result.get().password))

                        wb.backend.password = result.get().password

                        showWarnDialog("Success", "Password successfully changed!")
                    } catch (e: Exception) {
                        showWarnDialog("Error", e.toString())
                    }
                }
            }

        }

        accountLogOut.setOnAction {

            showWarnDialog("message", logout())
            wb.backend.username = ""
            wb.backend.userId = ""
            wb.backend.password = ""
            wb.backend.cookieValue = ""
            updateTitle(stage)
        }

    }

    private fun updateTitle(stage: Stage) {
        stage.titleProperty().bind(
            SimpleStringProperty(
                "WhiteBoard    - ${
                    if (wb.backend.boardname != "")
                        "Remote Board: ${wb.backend.boardname}"
                    else
                        "Local Board"
                }     - ${
                    if (wb.backend.username != "")
                        "Logged In: ${wb.backend.username}"
                    else
                        "Not Logged In"
                }"
            )
        )
    }


}