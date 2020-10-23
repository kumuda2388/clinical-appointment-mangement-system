package application;

import java.util.Calendar;
import java.util.regex.Pattern;
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.geometry.*;
import java.sql.*;

public class ABSHospital extends Application {
	String sql_UN="root"; //change it to your MySQL user name
	String sql_PW="password"; //change it to your MySQL password
	String email_id1;
	String password1;
	String password2;
	String first_name;
	String last_name;
	String birth_date;
	String ageOfPatient;
	String sex;
	String relationship_status;
	String emergency_phno;
	String blood_group;
	String childhood_Illness="[";
	String MedicalProblems="None";
	  public static void main(String args[]) { 
		     // Launch the application 
		     launch(); 
	  }
    @Override
  
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setTitle("ABS Hospital - Login");
        GridPane root = new GridPane(); // TLC (Top Layer Container) a root container for all other components, which in your case is the Button 
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10,10,10,10));
        root.setAlignment(Pos.TOP_CENTER);
        
        //BACKGROUND
        Image I = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg");
        BackgroundImage BGI = new BackgroundImage(I,
				BackgroundRepeat.REPEAT,  
                BackgroundRepeat.REPEAT,  
                BackgroundPosition.CENTER,  
                   BackgroundSize.DEFAULT);
		Background bg = new Background(BGI);
		root.setBackground(bg);
        
        //TITLE
  		String url="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
  		Image hc = new Image (url,80,80,true,true);
  		ImageView hcn = new ImageView(hc);
  		Label pd = new Label ("ABS Hospital",hcn);
  		pd.setFont(new Font("Copperplate Gothic Light",60));
  		root.add(pd, 0, 0);
  		//SEPARATOR AFTER THE TITLE
  		Separator sep = new Separator();
  		sep.setPrefSize(250,20);
  		root.add(sep, 0, 1);
  		
  		//PAGE NAME
  		Label headerLabel = new Label("Member Login");
  		headerLabel.setUnderline(true);
        root.add(headerLabel, 0,5);
        headerLabel.setFont(new Font("Copperplate Gothic Light", 35));
        
        //EMAIL ID
        Label emailLabel = new Label("Email ID: ");
        emailLabel.setFont(new Font("Century Gothic", 20));
        TextField emailField = new TextField();
        emailField.setPrefSize(250,40);
        HBox ei = new HBox();
        ei.setPadding(new Insets(10, 10, 10, 10));
	    ei.setSpacing(30);
	    ei.getChildren().addAll(emailLabel,emailField);
	    root.add(ei, 0, 10);
        
        //PASSWORD
        Label passwordLabel = new Label("Password: ");
        passwordLabel.setFont(new Font("Century Gothic", 20));
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefSize(250,40);
        HBox pw = new HBox();
        pw.setPadding(new Insets(10, 10, 10, 10));
	    pw.setSpacing(15);
	    pw.getChildren().addAll(passwordLabel,passwordField);
	    root.add(pw, 0, 11);
        
        //LOGIN BUTTON
        Button submitButton = new Button("Login");
        submitButton.setFont(new Font("Century Gothic",18));
        submitButton.setPrefSize(100,45);
        submitButton.setStyle("-fx-base: #7ecece;");
        submitButton.setDefaultButton(true);
        //SIGN UP BUTTON
        Button button = new Button("Sign Up");
        button.setFont(new Font("Century Gothic",18));
        button.setPrefSize(100,45);
        button.setStyle("-fx-base: #7ecece;");
        //Arranging the buttons
        HBox LS = new HBox();
        LS.setPadding(new Insets(10, 10, 10, 10));
	    LS.setSpacing(15);
	    LS.getChildren().addAll(submitButton,button);
	    LS.setAlignment(Pos.CENTER);
	    root.add(LS, 0,12);
        
        //Sign up message
	    Label label1=new Label("Not a member yet? Sign Up.");
        label1.setFont(new Font("Century Gothic", 14));
        HBox SUM = new HBox();
        SUM.setPadding(new Insets(10, 10, 10, 10));
	    SUM.getChildren().add(label1);
	    SUM.setAlignment(Pos.CENTER);
	    root.add(SUM, 0,13);
        
        //Event Handler for LOGIN button
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(emailField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, root.getScene().getWindow(), "Form Error!", "Please enter your email id");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, root.getScene().getWindow(), "Form Error!", "Please enter password");
                    return;
                }
                try{
	                	Class.forName("com.mysql.cj.jdbc.Driver");
	                    String email_id=emailField.getText();
	                    String password=passwordField.getText();
	               	    Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
	               	    String queryCheck = "SELECT * FROM Login_Credentials WHERE email = '"+email_id+"'";
	               	    
	               	    Statement st = conn.createStatement();
	               	    ResultSet rs = st.executeQuery(queryCheck); // execute the query, and get a java result set
	               	    // if this ID already exists, we quit
	               	    if(rs.absolute(1)) {
	                    	String Password1 = rs.getString("password");
	                    	if(Password1.equals(password)) 
	                    	{
	                    		showAlert(Alert.AlertType.CONFIRMATION, root.getScene().getWindow(), " ", "Login Successful!! " );
	                    		openMainMenu(email_id);
	                    		primaryStage.close();
	                    	}
	                    	else
	                    	{
	                    		showAlert(Alert.AlertType.ERROR, root.getScene().getWindow(), "Form Error!", "Incorrect password");
	                    		return; 
	                    	}
	                    }
	                    else 
	                    {
	                    	showAlert(Alert.AlertType.ERROR, root.getScene().getWindow(), "Form Error!", "This email_id is not registered");
	                    	return;
	                    }
               		}
                	catch(ClassNotFoundException e)
                	{
                		//Handle errors for Class.forName
                		e.printStackTrace();
                	}
                	catch(SQLException se)
                	{
                		se.printStackTrace();
                	}
                }
            });
        
        Scene scene = new Scene(root, 750,750); // create the scene and set the root, width and height
        primaryStage.setScene(scene); // set the scene
        primaryStage.setMaximized(true);
        primaryStage.show();
        /*END OF LOGIN PAGE*/

        /*START OF THE SIGN UP PAGE*/
        //Event Handler for the Sign Up button
        // add action listener, I will use the lambda style (which is data and code at the same time, read more about it in Oracle documentation)
        button.setOnAction(e->{
            //primaryStage.close(); // you can close the first stage from the beginning
            // create the structure again for the second GUI
            // Note that you CAN use the previous root and scene and just create a new Stage 
            //(of course you need to remove the button first from the root like this, root.getChildren().remove(0); at index 0)
        	
        	Stage secondStage = new Stage();
        	secondStage.setTitle("ABS Hospital - Sign Up");
        	//BACKGROUND
        	GridPane root2 = new GridPane();
            root2.setHgap(10);
            root2.setVgap(10);
            root2.setPadding(new Insets(10,10,10,10));
            root2.setAlignment(Pos.TOP_CENTER);
            
    		root2.setBackground(bg);
            
            //TITLE
      		String urlS="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
      		Image hcS = new Image (urlS,80,80,true,true);
      		ImageView hcnS = new ImageView(hcS);
      		Label pdS = new Label ("ABS Hospital",hcnS);
      		pdS.setFont(new Font("Copperplate Gothic Light",60));
      		root2.add(pdS, 0, 0);
      		//SEPARATOR AFTER THE TITLE
      		Separator sepS = new Separator();
      		sepS.setPrefSize(250,20);
      		root2.add(sepS, 0, 1);
      		
      		//PAGE NAME
      		Label headerLabelS = new Label("Member Sign Up");
      		headerLabelS.setUnderline(true);
            root2.add(headerLabelS, 0,5);
            headerLabelS.setFont(new Font("Copperplate Gothic Light", 35));

            //EMAIL ID
            Label emailLabel1 = new Label("Email ID: ");
            emailLabel1.setFont(new Font("Century Gothic", 20));
            TextField emailField1 = new TextField();
            emailField1.setPrefSize(250,40);
            HBox eiS = new HBox();
            eiS.setPadding(new Insets(10, 10, 10, 10));
    	    eiS.setSpacing(110);
    	    eiS.getChildren().addAll(emailLabel1,emailField1);
    	    root2.add(eiS, 0, 10);
            
    	    //PASSWORD
            Label passwordLabel1 = new Label("Password: ");
            passwordLabel1.setFont(new Font("Century Gothic", 20));
            PasswordField passwordField1 = new PasswordField();
            passwordField1.setPrefSize(250,40);
            Tooltip passTP = new Tooltip("Password should be no longer than 15 characters");
            passwordField1.setTooltip(passTP);
            HBox pwS = new HBox();
            pwS.setPadding(new Insets(10, 10, 10, 10));
    	    pwS.setSpacing(98);
    	    pwS.getChildren().addAll(passwordLabel1,passwordField1);
    	    root2.add(pwS, 0, 11);
    	    
    	    //CONFIRM PASSWORD
    	    Label CpasswordLabel1 = new Label("Confirm Password: ");
            CpasswordLabel1.setFont(new Font("Century Gothic", 20));
            PasswordField CpasswordField1 = new PasswordField();
            CpasswordField1.setPrefSize(250,40);
            HBox CpwS = new HBox();
            CpwS.setPadding(new Insets(10, 10, 10, 10));
    	    CpwS.setSpacing(15);
    	    CpwS.getChildren().addAll(CpasswordLabel1,CpasswordField1);
    	    root2.add(CpwS, 0, 12);
            
    	    //SUBMIT BUTTON (to enter the details into the database)
            Button submitButton1 = new Button("Submit");
            submitButton1.setFont(new Font("Century Gothic",18));
            submitButton1.setPrefSize(100,45);
            submitButton1.setStyle("-fx-base: #7ecece;");
            submitButton1.setDefaultButton(true);
            //BACK button to go back to the LOGIN Page
            Button Button2 = new Button("Back");
            Button2.setFont(new Font("Century Gothic",18));
            Button2.setPrefSize(100,45);
            Button2.setStyle("-fx-base: #7ecece;");
            HBox SLS = new HBox();
            SLS.setPadding(new Insets(10, 10, 10, 10));
    	    SLS.setSpacing(15);
    	    SLS.getChildren().addAll(submitButton1,Button2);
    	    SLS.setAlignment(Pos.CENTER);
    	    root2.add(SLS, 0,13);
            
    	    //Event Handler for the BACK button
            Button2.setOnAction(se->{
            	  // create the scene and set the root, width and height
                 primaryStage.setScene(scene); // set the scene
                 primaryStage.show();
                 secondStage.close();
            });
            
            //Event Handler for SUBMIT button in the Sign Up page
            submitButton1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	if(emailField1.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, root2.getScene().getWindow(), "Form Error!", "Please enter your email id");
                        return;
                    }
                	else
                	{
                		email_id1 = emailField1.getText();
                		if(isValid(email_id1));
                		else {
                			showAlert(Alert.AlertType.ERROR, root2.getScene().getWindow(), "Form Error!", "enter a valid email_id");
                        return;
                	}}
                    if(passwordField1.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, root2.getScene().getWindow(), "Form Error!", "Please enter a password");
                        return;
                    }
                    if(CpasswordField1.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, root2.getScene().getWindow(), "Form Error!", "Please confirm entered password");
                        return;
                    }
                    
                    if(!passwordField1.getText().equals(CpasswordField1.getText()))  {
                    	showAlert(Alert.AlertType.ERROR,root2.getScene().getWindow(),"form Error!","Password mismatch");
                    return;
                    }
                     try{
                    	 Class.forName("com.mysql.cj.jdbc.Driver");
                    	 Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
                         email_id1 = emailField1.getText();
                         password1=passwordField1.getText();
                         password2=CpasswordField1.getText();
                         String dbop="INSERT INTO Login_Credentials VALUES ('"+email_id1+"','"+password1+"')";
                         Statement st = con.prepareStatement(dbop);
                         st.executeUpdate(dbop);
                     }
                     catch(ClassNotFoundException e){
                         //Handle errors for Class.forName
                         e.printStackTrace();
                      }
                     catch(SQLException se)
                     {
                    		            if(se.getMessage().contains("Duplicate")) {
                    		            	showAlert(Alert.AlertType.ERROR,root2.getScene().getWindow(),"form Error!","email id already registered");
                    	                    return;     
                    		            }
                    		     
                    		    
                     }
       
                    showAlert(Alert.AlertType.CONFIRMATION, root2.getScene().getWindow(), "Registration Successful!", emailField1.getText()+" has been registered successfully. Please login.");
                }
            });
            Scene secondScene = new Scene(root2, 500,500);
            secondStage.setScene(secondScene); // set the scene
            secondStage.setMaximized(true);
            secondStage.show();
            primaryStage.close(); // close the first stage (Window)
        });
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.initOwner(owner);
    alert.show();
    }
    
    private static boolean isValid(String email)
    {
    	String emailRegex="^[a-zA-Z0-9_+&*-]+(?:\\."+
    "[a-zA-Z0-9_+&*-]+)*@"+
    			"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
    "A-Z]{2,7}$";
    	Pattern pat=Pattern.compile(emailRegex);
    	if(email == null)
    		return false;
    	return pat.matcher(email).matches();
    }
    //Opening main menu
    public void openMainMenu(String emid)
    {
    	Stage myMainStage = new Stage();
    	myMainStage.setTitle("ABS Hospital");
		GridPane rootNode = new GridPane();
		rootNode.setVgap(10);
		rootNode.setHgap(10);
		rootNode.setMinSize(800, 750);
		rootNode.setPadding(new Insets(10,10,10,10));
		rootNode.setAlignment(Pos.TOP_CENTER);
		
		//BACKGROUND IMAGE
		Image I = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg");
		BackgroundImage BGI = new BackgroundImage(I,
				BackgroundRepeat.REPEAT,  
                BackgroundRepeat.REPEAT,  
                BackgroundPosition.CENTER,  
                   BackgroundSize.DEFAULT);
		Background bg = new Background(BGI);
		rootNode.setBackground(bg);
		
		//TITLE
		String url="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
		Image hc = new Image (url,80,0,true,true);
		ImageView hcn = new ImageView(hc);
		Label pd = new Label ("ABS Hospital",hcn);
		pd.setFont(new Font("Copperplate Gothic Light",60));
		rootNode.add(pd, 0, 0);
		//SEPARATOR AFTER THE TITLE
		Separator sep = new Separator();
		sep.setPrefSize(250,20);
		rootNode.add(sep, 0, 1);
		
		//WELCOME 
		Label wel = new Label("Welcome");
		wel.setFont(new Font("Copperplate Gothic Light",40));
		rootNode.add(wel, 0, 3);
		
		//MESSAGE
		Label msg = new Label("What would you like to do today?");
		msg.setFont(new Font("Century Gothic",20));
		rootNode.add(msg, 0, 5);
		
		//Book an appointment button
		String bbUrl  = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Simpleicons_Business_calendar-with-a-clock-time-tools.svg/768px-Simpleicons_Business_calendar-with-a-clock-time-tools.svg.png";
		Image bb = new Image(bbUrl,45,45,true,true);
		ImageView bbIV = new ImageView(bb);
		Button book = new Button("Book an Appointment",bbIV);
		book.setContentDisplay(ContentDisplay.TOP);
		book.setPrefSize(200, 150);
		book.setFont(new Font("Century Gothic",15));
		book.setStyle("-fx-base:  #7ecece");
		/*event handler to open new window on clicking book an appointment button*/
		book.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				appointmentBooking(emid);
			}
		});
		//Edit profile button
		String epUrl  = "https://cdn0.iconfinder.com/data/icons/mobile-development-svg-icons/60/Edit_user-512.png";
		Image epicon = new Image(epUrl,45,45,true,true);
		ImageView epIV = new ImageView(epicon);
		Button  ep = new Button("Edit Profile",epIV);
		ep.setContentDisplay(ContentDisplay.TOP);
		ep.setPrefSize(200, 150);
		ep.setFont(new Font("Century Gothic",15));
		ep.setStyle("-fx-base:  #7ecece");
		
		/*event handler to open new window on clicking edit profile button*/
		ep.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				showEditProfile(emid);
			}
		});
		
		HBox CH1 = new HBox();
		CH1.setPadding(new Insets(10, 10, 10, 10));
	    CH1.setSpacing(30);
	    CH1.getChildren().addAll(book,ep);
		rootNode.add(CH1, 0, 7);
		
		//Medical Report Button
		String mrUrl  = "https://cdn3.iconfinder.com/data/icons/medical-vol-1-2/512/13-512.png";
		Image mricon = new Image(mrUrl,45,45,true,true);
		ImageView mrIV = new ImageView(mricon);
		Button  mr = new Button("View Medical Report",mrIV);
		mr.setContentDisplay(ContentDisplay.TOP);
		mr.setPrefSize(200, 150);
		mr.setFont(new Font("Century Gothic",15));
		mr.setStyle("-fx-base:  #7ecece");
		/*event handler to open new window on clicking medical report button*/
		mr.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				Report(emid);
			}
		});
		
		//Prescription Details Button
		String pdUrl  = "https://image.flaticon.com/icons/png/512/474/474620.png";
		Image pdicon = new Image(pdUrl,45,45,true,true);
		ImageView pdIV = new ImageView(pdicon);
		Button  presD = new Button("View Prescription Details",pdIV);
		presD.setContentDisplay(ContentDisplay.TOP);
		presD.setPrefSize(200, 150);
		presD.setFont(new Font("Century Gothic",15));
		presD.setStyle("-fx-base:  #7ecece");
		presD.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				Prescription(emid);
			}
		});
		
		HBox CH2 = new HBox();
		CH2.setPadding(new Insets(10, 10, 10, 10));
	    CH2.setSpacing(30);
	    CH2.getChildren().addAll(mr,presD);
		rootNode.add(CH2, 0, 8);
		
		//Location
		String locURL="http://www.trollbeads.com/on/demandware.static/Sites-Trollbeads_CountrySelector-Site/-/default/dw8475af3c/images/google-location-icon-turJBT-clipart.png";
		Image loc = new Image(locURL,30,30,true,true);
		ImageView locIV = new ImageView(loc);
		Label locL = new Label("92 Cypress Street\r\nLondonderry, NH 03053",locIV);
		locL.setFont(new Font("Century Gothic",15));
		locL.setContentDisplay(ContentDisplay.LEFT);
		rootNode.add(locL, 0, 10);
		
		//Phone Number
		String phURL="https://cdn3.iconfinder.com/data/icons/communication-1/100/old_phone-512.png";
		Image ph = new Image(phURL,30,30,true,true);
		ImageView phIV = new ImageView(ph);
		Label phL = new Label("209-375-5004",phIV);
		phL.setFont(new Font("Century Gothic",15));
		phL.setContentDisplay(ContentDisplay.LEFT);
		rootNode.add(phL, 0, 11);
		
		//EmailID
		String MURL="https://cdn4.iconfinder.com/data/icons/eldorado-work/40/mail-512.png";
		Image Mi = new Image(MURL,30,30,true,true);
		ImageView MIV = new ImageView(Mi);
		Label M = new Label("abs.hospital@hops.com",MIV);
		M.setFont(new Font("Century Gothic",15));
		M.setContentDisplay(ContentDisplay.LEFT);
		rootNode.add(M, 0, 12);
		
		Scene myScene = new Scene(rootNode);
		myMainStage.setMaximized(true);
		myMainStage.setScene(myScene);
		myMainStage.show();
	}
    /*To Open Appointments Page*/
    public void appointmentBooking(String emid) {
    	Stage primarystage=new Stage();
    	primarystage.setTitle("ABS Hospital - Appointment Booking"); 
	     GridPane root = new GridPane(); 
	     root.setHgap(10);
	     root.setVgap(10);
	     root.setMinSize(800, 750);
		 root.setPadding(new Insets(10,10,10,10));
		 root.setAlignment(Pos.TOP_CENTER);
	     
	     //BACKGROUND IMAGE
		 Image I = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg");
	     BackgroundImage BGI = new BackgroundImage(I,
					BackgroundRepeat.REPEAT,  
	                BackgroundRepeat.REPEAT,  
	                BackgroundPosition.CENTER,  
	                   BackgroundSize.DEFAULT);
			Background bg = new Background(BGI);
			root.setBackground(bg);
		 
		 //TITLE
		 String url="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
		 Image hc = new Image (url,80,0,true,true);
		 ImageView hcn = new ImageView(hc);
		 Label pd = new Label ("ABS Hospital",hcn);
		 pd.setFont(new Font("Copperplate Gothic Light",60));
		 root.add(pd, 0, 0);
		 
		  //SEPARATOR AFTER THE TITLE
		 Separator sep = new Separator();
		 sep.setPrefSize(250,20);
		 root.add(sep, 0, 1);
		 
		 //PAGE NAME
		 Text text3 = new Text();
		 text3.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 25)); 
		 text3.setText("Who would you like to see?");
		 root.add(text3, 0,5);
	    
		 ///BUTTONS OF HOSPITAL DEPARTMENTS/
		 //ENT DEPARTMENT BUTTON
		 String entU  = "https://cdn3.iconfinder.com/data/icons/medical-specialties-4/256/Medical_Specialties-07-512.png";
		 Image entI = new Image(entU,50,50,true,true);
		 ImageView entIV = new ImageView(entI);
	     Button submitButton2 = new Button("ENT Speacialist",entIV);
	     submitButton2.setContentDisplay(ContentDisplay.TOP);
	     submitButton2.setPrefSize(250,150);
	     submitButton2.setFont(new Font("Century Gothic",18));
	     submitButton2.setStyle("-fx-base:  	#7ecece");
	     
	     //PEDIATRICS DEPARTMENT
	     String pU  = "https://cdn4.iconfinder.com/data/icons/gynecology/64/26_gynecology-baby-born-child-512.png";
		 Image pI = new Image(pU,50,50,true,true);
		 ImageView pIV = new ImageView(pI);
	     Button submitButton3 = new Button("Pediatrician",pIV);
	     submitButton3.setContentDisplay(ContentDisplay.TOP);
	     submitButton3.setFont(new Font("Century Gothic",18));
	     submitButton3.setPrefSize(250,150);
	     submitButton3.setStyle("-fx-base: #7ecece");
	     
	     //GYNOCOLOGY DEPARTMENT
	     String eU  = "https://cdn1.iconfinder.com/data/icons/medical-specialties-set-2-1/256/15-512.png";
		 Image eI = new Image(eU,50,50,true,true);
		 ImageView eIV = new ImageView(eI);
	     Button submitButton4 = new Button("Gynocologist",eIV);
	     submitButton4.setContentDisplay(ContentDisplay.TOP);
	     submitButton4.setFont(new Font("Century Gothic",18));
	     submitButton4.setPrefSize(250,150);
	     submitButton4.setStyle("-fx-base:  	#7ecece");
	     
	     //NEUROLOGY DEPARTMENT
	     String nU  = "https://cdn3.iconfinder.com/data/icons/medical-specialties-4/256/Medical_Specialties-08-512.png";
		 Image nI = new Image(nU,50,50,true,true);
		 ImageView nIV = new ImageView(nI);
	     Button submitButton5 = new Button("Neurologist",nIV);
	     submitButton5.setContentDisplay(ContentDisplay.TOP);
	     submitButton5.setFont(new Font("Century Gothic",18));
	     submitButton5.setPrefSize(250,150);
	     submitButton5.setStyle("-fx-base:  	#7ecece");
	     
	     //GENERAL PHYSICIAN DEPARTMENT
	     String gpU  = "https://images.vexels.com/media/users/3/151710/isolated/lists/b76de73edf814db22e2ae74c01739b5c-doctor-avatar-stroke-icon.png";
		 Image gpI = new Image(gpU,50,50,true,true);
		 ImageView gpIV = new ImageView(gpI);
	     Button submitButton6 = new Button("General Physician",gpIV);
	     submitButton6.setContentDisplay(ContentDisplay.TOP);
	     submitButton6.setFont(new Font("Century Gothic",18));
	     submitButton6.setPrefSize(250,150);
	     submitButton6.setStyle("-fx-base:  #7ecece");
	     
	     //ORTHOPEDICS DEPARTMENT
	     String oU  = "https://cdn4.iconfinder.com/data/icons/orthopedics/92/icon88-21-512.png";
		 Image oI = new Image(oU,50,50,true,true);
		 ImageView oIV = new ImageView(oI);
	     Button submitButton7 = new Button("Orthopedic",oIV);
	     submitButton7.setContentDisplay(ContentDisplay.TOP);
	     submitButton7.setFont(new Font("Century Gothic",18));
	     submitButton7.setPrefSize(250,150);
	     submitButton7.setStyle("-fx-base:  	#7ecece");
	     
	     //Setting the buttons on the grid
	     HBox row1 = new HBox();
	     row1.setPadding(new javafx.geometry.Insets(10,10,10,10));
	     row1.setSpacing(30);
	     row1.getChildren().addAll(submitButton2,submitButton3,submitButton4);
	     root.add(row1, 0, 7);
	     HBox row2 = new HBox();
	     row2.setPadding(new javafx.geometry.Insets(10,10,10,10));
	     row2.setSpacing(30);
	     row2.getChildren().addAll(submitButton5,submitButton6,submitButton7);
	     root.add(row2, 0, 8);
	     
	     //ENT BUTTON setOnAction
	      submitButton2.setOnAction(e-> {
	    	 // Set title for the stage 
		     primarystage.setTitle("ABS Hospital - Appointment Booking"); 
		     GridPane ro = new GridPane(); 
		     ro.setHgap(1);
		     ro.setVgap(2);
		     ro.setMinSize(800, 750);
			 ro.setPadding(new Insets(10,5,10,10));
			 ro.setAlignment(Pos.TOP_CENTER);
		     		    
			 //TITLE
			 String urle="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
			 Image hce = new Image (urle,80,0,true,true);
			 ImageView hcne = new ImageView(hce);
			 Label pde = new Label ("ABS Hospital",hcne);
			 pde.setFont(new Font("Copperplate Gothic Light",60));
			 ro.add(pde, 0, 0);
			 //SEPARATOR AFTER THE TITLEy other
			 Separator sepe = new Separator();
			 sepe.setPrefSize(250,20);
			 ro.add(sepe, 0, 1);
			 
			 Label bke = new Label ("BOOK  YOUR  APPOINTMENT  : ");
			 bke.setFont(new Font("Copperplate Gothic Light",30));
			 ro.add(bke,0, 24); 
		   
			 Label eds = new Label ("ENT DOCTORS   :");
			 eds.setFont(new Font("Copperplate Gothic Light",20));
			 ro.add(eds,0, 65); 
		   
		     // doctors
		     String doctors[] = 
		                { "Dr.SAM-ENT-SPECIALIST",
		                  "Dr.PETER-ENT-SPECIALIST",
		                  "Dr.ALAN-ENT-SPECIALIST",
		                  "Dr.LUCIFER-ENT-SPECIALIST"}; 
	
		     // Create a combo box 
		      ComboBox combo_box = new ComboBox(FXCollections .observableArrayList(doctors)); 
		    		   
		   // DATE
		 		Label edate = new Label ("   DATE : ");
		 		edate.setFont(new Font("Copperplate Gothic Light",18));
		 		ro.add(edate, 0, 80);
		 		DatePicker datePicker = new DatePicker();
		 		ro.add(datePicker, 1, 80);
		         
		 		//timings
		 		Label etime = new Label("TIMINGS: ");
				etime.setFont(new Font(18));
				ro.add(etime, 0,95);
				
				RadioButton m = new RadioButton("\t09:00:00 \t");
				RadioButton a = new RadioButton("\t12:00:00\t");
				RadioButton o = new RadioButton("\t03:00:00\t");
				ToggleGroup tm = new ToggleGroup();
				m.setToggleGroup(tm);
				a.setToggleGroup(tm);
				o.setToggleGroup(tm);
				HBox timee1 = new HBox();
				timee1.setPadding(new Insets(10, 10, 10, 10));
			    timee1.setSpacing(10);
			    timee1.getChildren().addAll(m, a, o);
				ro.add(timee1,1, 95);
			
		      //APPOINTMENT STATUS
		      ro.add(combo_box,1,65);
		      Label STe = new Label (" APPOINTMENT STATUS : ");
			  STe.setFont(new Font("Copperplate Gothic Light",30));
			  ro.add(STe,0,150); 
		     // Label to display the selected menu item 
		      Label selected1 = new Label("\t\t\tDoctors appointment not yet booked"); 
		      selected1.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 20));
		      ro.add(selected1,0,180);
	
		     Button OKButton = new Button("CONFIRM APPOINTMENT");
		     OKButton.setPrefSize(250,50);
		     OKButton.setFont(new Font("Century Gothic",18));
		     OKButton.setStyle("-fx-base:  #7ecece");
		     ro.add(OKButton,3,200);
		     //Ok button when clicked		 
		     OKButton.setOnAction(e1->{
		                   
			    	 Connection conn = null;
			         Statement stm = null;
			         RadioButton t=(RadioButton)tm.getSelectedToggle();
			         Calendar cal = Calendar.getInstance();
						int curYear = cal.get(Calendar.YEAR);
						int curMonth = cal.get(Calendar.MONTH);
						curMonth++;
						int curDate = cal.get(Calendar.DATE);
			         try {
			       
			        String Doctors_details=combo_box.getValue().toString();
			        String app_dates=datePicker.getValue().toString();			       
			        String app_time=t.getText(); 
			        String dept = "ENT";
			        Alert noti = new Alert(AlertType.NONE);
			        if(datePicker.getValue().getYear()>=curYear && datePicker.getValue().getMonthValue()>=curMonth &&datePicker.getValue().getDayOfMonth()>=curDate)
			        {
			        	//register for JDBC drive
			        	 Class.forName("com.mysql.cj.jdbc.Driver");
			        	 //open a connection 
			        	 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
			        	 //execute a query
			        	 stm = conn.createStatement();
			        	 String app;
			        	 int count=0;
			        	 String checkquery="SELECT *from appointment WHERE appointment_date='"+ app_dates+"'and dept='"+dept+"'AND eid='"+emid+"'";//add email
			        	 ResultSet chk=conn.createStatement().executeQuery(checkquery);
			        	 if(chk.next()) {
			        		 Alert noti1 = new Alert(AlertType.NONE);
			        		 noti1.setAlertType(AlertType.ERROR);
					        	noti1.setTitle("WRONG VALUE ENTERED");
								noti1.setHeaderText("ERROR");
								noti1.setContentText("You already have a booking for this date.");
								noti1.showAndWait();
			        	 }
			        	 else {
			        		 String selectquery="SELECT COUNT(*) from appointment WHERE Doctors_detail='"+Doctors_details+"'and appointment_date='"+ app_dates+"' and appointment_time='"+app_time+"' and dept='"+dept+"'";
				        	 ResultSet rs=conn.createStatement().executeQuery(selectquery);
				        	 while(rs.next()) {
				        		 count+=rs.getInt("COUNT(*)");
				        	 }	
				        	 //System.out.println(count);
				        	 if(count<15)
				        	 {
				                 app = "INSERT INTO appointment(eid,Doctors_detail,appointment_date,appointment_time,dept)values('"+emid+"','"+Doctors_details+"','"+ app_dates+"','"+app_time+"','"+dept+"')";
				                 try {
				    	    		 RadioButton selTe = (RadioButton) tm.getSelectedToggle();
				    	    		 String tme = selTe.getText();
				    			selected1.setText("Your appointment with \n: "+"DOCTOR  :  "+combo_box.getValue()
				    	  +"DATE : "+ datePicker.getValue()+"TIME : "+tme+" is booked for the time slot of 2-HOURS"
				    	    			);
				    	    	 ro.add(selected1,0,180);
				    	           final String value=(String) combo_box.getValue();
				    	     } catch(Exception eh) {System.out.println(eh);}
				        	     stm.execute(app);
				        	 }
				        	 else {
				        		 RadioButton selTe = (RadioButton) tm.getSelectedToggle();
			    	    		 String tme = selTe.getText();
				        		 selected1.setText("\t\tSORRY Your appointment with : \t\t\n "+"DOCTOR  :  "+combo_box.getValue()
		    	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tme+"\n cannot be booked");
				        	 }
				        
				        	 stm.close();
			        	 }
			        	 
			        }
			        else
			        {
			        	noti.setAlertType(AlertType.ERROR);
			        	noti.setTitle("WRONG VALUE ENTERED");
						noti.setHeaderText("ERROR");
						noti.setContentText("You can not book an appointment for the selected date.Please select a valid date.");
						noti.showAndWait();
			        }
			       
			        	 
			         }//main try closing
			         catch(ClassNotFoundException er)
			         { System.out.println(er);}
			         catch(SQLException se)
			         { System.out.println(se);	}
	         	
		         }//handler bracket
		     );//closing set on action parenthesis
		          
	 			// Create a scene 
		     Scene scene = new Scene(ro); 
		     Stage secondStage = new Stage();
		     secondStage.setMaximized(true);
	
		     Scene secondscene = null;
				secondStage.setScene(scene); // set the scene
				secondStage.setTitle("ABS Hospital - Appointment Booking"); 
				
	            secondStage.show();
	            primarystage.close();
	           
	    		ro.setBackground(bg);
	});
	     
	     submitButton3.setOnAction(e-> {
		     
	    	 // Set title for the stage 
		     primarystage.setTitle("ABS Hospital - Appointment Booking"); 
		     GridPane ro1 = new GridPane(); 
		     ro1.setHgap(1);
		     ro1.setVgap(2);
		     ro1.setMinSize(800, 750);
			 ro1.setPadding(new Insets(10,5,10,10));
			 ro1.setAlignment(Pos.TOP_CENTER);
		     
		    
			 //TITLE
			 String url2="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
			 Image hc2 = new Image (url2,80,0,true,true);
			 ImageView hcn2 = new ImageView(hc2);
			 Label pd2 = new Label ("ABS Hospital",hcn2);
			 pd2.setFont(new Font("Copperplate Gothic Light",60));
			 ro1.add(pd2, 0, 0);
			 //SEPARATOR AFTER THE TITLE
			 Separator sep2 = new Separator();
			 sep2.setPrefSize(250,20);
			 ro1.add(sep2, 0, 1);
			 

			 Label bk = new Label ("BOOK  YOUR  APPOINTMENT  : ");
			 bk.setFont(new Font("Copperplate Gothic Light",30));
			 ro1.add(bk,0, 24); 
		   
			 Label pds = new Label ("PEDIATRICIAN DOCTORS   :");
			 pds.setFont(new Font("Copperplate Gothic Light",20));
			 ro1.add(pds,0, 65); 
		   
	
		     // doctors
		      
		     String doctors[] = 
		                { "Dr.SHILPA-PEDIATRICIAN",
		                  "Dr.CHRISTIN-PEDIATRICIAN",
		                  "Dr.SHELDON-PEDIATRICIAN",
		                  "Dr.LEONARD-PEDIATRICIAN"}; 
	
		     // Create a combo box 
		      ComboBox combo_box = new ComboBox(FXCollections .observableArrayList(doctors)); 
		        
		   // DATE
		 		Label date = new Label ("   DATE : ");
		 		date.setFont(new Font("Copperplate Gothic Light",18));
		 		ro1.add(date, 0, 80);
		 		DatePicker datePicker = new DatePicker();
		 		ro1.add(datePicker, 1, 80);
		         
		 		//timings
				Label time = new Label("TIMINGS: ");
				time.setFont(new Font(18));
				ro1.add(time, 0,95);
				RadioButton m = new RadioButton("\t09:00:00\t");
				RadioButton a = new RadioButton("\t12:00:00\t");
				RadioButton o = new RadioButton("\t03:00:00\t");
				ToggleGroup tm = new ToggleGroup();
				m.setToggleGroup(tm);
				a.setToggleGroup(tm);
				o.setToggleGroup(tm);
				HBox time1 = new HBox();
				time1.setPadding(new Insets(10, 10, 10, 10));
			    time1.setSpacing(10);
			    time1.getChildren().addAll(m, a, o);
				ro1.add(time1,1, 95);
			
		      //APPOINTMENT STATUS
		      ro1.add(combo_box,1,65);
		      Label ST = new Label (" APPOINTMENT STATUS : ");
				 ST.setFont(new Font("Copperplate Gothic Light",30));
				 ro1.add(ST,0,150); 
		     // Label to display the selected menu item 
		      Label selected1 = new Label("\t\t\tDoctors appointment not yet booked"); 
		      selected1.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 20));
		      ro1.add(selected1,0,180);
	
		     Button OKButton = new Button("CONFIRM APPOINTMENT");
		     OKButton.setPrefSize(250,50);
		     OKButton.setFont(new Font("Century Gothic",18));
		     OKButton.setStyle("-fx-base:  #7ecece");
		     ro1.add(OKButton,3,200);
		     		     
		         OKButton.setOnAction(e1->{
		        	 Connection conn = null;
			         Statement stm = null;
			         RadioButton t1 = (RadioButton) tm.getSelectedToggle();
			         Calendar cal = Calendar.getInstance();
						int curYear = cal.get(Calendar.YEAR);
						int curMonth = cal.get(Calendar.MONTH);
						curMonth++;
						int curDate = cal.get(Calendar.DATE);
			        try {
			        	 String Doctors_d_p=combo_box.getValue().toString();
					        String app_dates_p=datePicker.getValue().toString();			       
					        String app_time_p=t1.getText(); 
					        String dept_p = "PEDIATRICIAN";
					        Alert noti = new Alert(AlertType.NONE);
					        if(datePicker.getValue().getYear()>=curYear && datePicker.getValue().getMonthValue()>=curMonth &&datePicker.getValue().getDayOfMonth()>=curDate) {
					        	//register for JDBC drive
					        	 Class.forName("com.mysql.cj.jdbc.Driver");
					        	 //open a connection 
					        	 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
					        	 //execute a query
					        	 stm = conn.createStatement();
					        	 String app1;
					        	 int count1=0;
					        	 String checkquery="SELECT *from appointment WHERE appointment_date='"+ app_dates_p+"'and dept='"+dept_p+"' and eid='"+emid+"'";//add email
					        	 ResultSet chk=conn.createStatement().executeQuery(checkquery);
					        	 if(chk.next()) {
					        		 Alert noti1 = new Alert(AlertType.NONE);
					        		 noti1.setAlertType(AlertType.ERROR);
							        	noti1.setTitle("WRONG VALUE ENTERED");
										noti1.setHeaderText("ERROR");
										noti1.setContentText("You already have a booking for this date.");
										noti1.showAndWait();
					        	 }
					        	 else {
					        		 String selectquery1="SELECT COUNT(*) from appointment WHERE Doctors_detail='"+Doctors_d_p+"'and appointment_date='"+ app_dates_p+"' and appointment_time='"+app_time_p+"' and dept='"+dept_p+"'";
						        	 ResultSet rs1=conn.createStatement().executeQuery(selectquery1);
						        	 while(rs1.next()) {
						        		 count1+=rs1.getInt("COUNT(*)");
						        	 }	
						        	 //System.out.println(count);
						        	 if(count1<15)
						        	 {
						                 app1 = "INSERT INTO appointment(eid,Doctors_detail,appointment_date,appointment_time,dept)values('"+emid+"','"+Doctors_d_p+"','"+ app_dates_p+"','"+app_time_p+"','"+dept_p+"')";
						                 try {
						    	    		 RadioButton selTp= (RadioButton) tm.getSelectedToggle();
						    	    		 String tmp = selTp.getText();
						    			selected1.setText("\t\tYour appointment with : \t\t\n\n "+"DOCTOR  :  "+combo_box.getValue()
						    	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmp+"\n is booked for the time slot of 2-HOURS"
						    	    			);
						    	    	 ro1.add(selected1,0,180);
						    	           final String value=(String) combo_box.getValue();
				                             }
						    	     catch(Exception eh) {System.out.println(eh);}
					        	     stm.execute(app1);
					        	    }
					        	 else {
					        		 RadioButton selTp = (RadioButton) tm.getSelectedToggle();
				    	    		 String tmp = selTp.getText();
					        		 selected1.setText("\t\tSORRY Your appointment with : \t\t\n "+"DOCTOR  :  "+combo_box.getValue()
			    	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmp+"\n cannot be booked");
					        	      }
					        
					        	 stm.close();
					        	 }
					        	 
					        }
					        else
					        {
					        	noti.setAlertType(AlertType.ERROR);
					        	noti.setTitle("WRONG VALUE ENTERED");
								noti.setHeaderText("ERROR");
								noti.setContentText("You can not book an appointment for the selected date.Please select a valid date.");
								noti.showAndWait();
					        }
					       					        	 
				         }
			        	 catch(ClassNotFoundException er){
	                         //Handle errors for Class.forName
	                         System.out.println(er);
	                      }
	                    catch(SQLException se)
	                     {           
	                    	 System.out.println(se);
	                    		}
	          	
			         });
		     	
			// Create a scene 
		     Scene scene = new Scene(ro1); 
		     Stage secondStage = new Stage();
		     secondStage.setMaximized(true);
	
		     Scene secondscene = null;
				secondStage.setScene(scene); // set the scene
				secondStage.setTitle("ABS Hospital - Appointment Booking"); 
				
	            secondStage.show();
	            primarystage.close();
	           
	    		ro1.setBackground(bg);
	    		 
		});
	     //gynocogist 
	     submitButton4.setOnAction(e2-> {
		     
	    	 // Set title for the stage 
		     primarystage.setTitle("ABS Hospital - Appointment Booking"); 
		     GridPane ro2 = new GridPane(); 
		     ro2.setHgap(1);
		     ro2.setVgap(2);
		     ro2.setMinSize(800, 750);
			 ro2.setPadding(new Insets(10,5,10,10));
			 ro2.setAlignment(Pos.TOP_CENTER);
		     
		     //TITLE
			 String url2="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
			 Image hc2 = new Image (url2,80,0,true,true);
			 ImageView hcn2 = new ImageView(hc2);
			 Label pd2 = new Label ("ABS Hospital",hcn2);
			 pd2.setFont(new Font("Copperplate Gothic Light",60));
			 ro2.add(pd2, 0, 0);
			 //SEPARATOR AFTER THE TITLE
			 Separator sep2 = new Separator();
			 sep2.setPrefSize(250,20);
			 ro2.add(sep2, 0, 1);
			 
			 Label bk2 = new Label ("BOOK  YOUR  APPOINTMENT  : ");
			 bk2.setFont(new Font("Copperplate Gothic Light",30));
			 ro2.add(bk2,0, 24); 
		   
			 Label Gds = new Label ("GYNOCOLOGIST DOCTORS   :");
			 Gds.setFont(new Font("Copperplate Gothic Light",20));
			 ro2.add(Gds,0, 65); 
		   
			     // doctors
		      String doctors[] = 
		                { "Dr.SRIDHAR-GYNOCOLOGIST",
		                  "Dr.PAVAN-GYNOCOLOGIST",
		                  "Dr.ADIL-GYNOCOLOGIST",
		                  "Dr.FRANK-GYNOCOLOGIST"}; 
	
		     // Create a combo box 
		      ComboBox combo_box = new ComboBox(FXCollections .observableArrayList(doctors)); 
		    
		   // DATE
		 		Label date2 = new Label ("   DATE : ");
		 		date2.setFont(new Font("Copperplate Gothic Light",18));
		 		ro2.add(date2, 0, 80);
		 		DatePicker datePicker = new DatePicker();
		 		ro2.add(datePicker, 1, 80);
		         
		 		//timings
				Label time2 = new Label("TIMINGS: ");
				time2.setFont(new Font(18));
				ro2.add(time2, 0,95);
				RadioButton m = new RadioButton("\t09:00:00\t");
				RadioButton a = new RadioButton("\t12:00:00\t");
				RadioButton o = new RadioButton("\t03:00:00\t");
				ToggleGroup tm = new ToggleGroup();
				m.setToggleGroup(tm);
				a.setToggleGroup(tm);
				o.setToggleGroup(tm);
				HBox timeg1 = new HBox();
				timeg1.setPadding(new Insets(10, 10, 10, 10));
			    timeg1.setSpacing(10);
			    timeg1.getChildren().addAll(m, a, o);
				ro2.add(timeg1,1, 95);
			
		      //APPOINTMENT STATUS
		      ro2.add(combo_box,1,65);
		      Label ST2 = new Label (" APPOINTMENT STATUS : ");
				 ST2.setFont(new Font("Copperplate Gothic Light",30));
				 ro2.add(ST2,0,150); 
		     // Label to display the selected menu item 
		      Label selected1 = new Label("\t\t\tDoctors appointment not yet booked"); 
		      selected1.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 20));
		      ro2.add(selected1,0,180);
	
		     Button OKButton = new Button("CONFIRM APPOINTMENT");
		     OKButton.setPrefSize(250,50);
		     OKButton.setFont(new Font("Century Gothic",18));
		     OKButton.setStyle("-fx-base:  #7ecece");
		     ro2.add(OKButton,3,200);
		     	     
		     OKButton.setOnAction(e1->{
                
		    	 Connection conn = null;
		         Statement stm = null;
		         RadioButton t=(RadioButton)tm.getSelectedToggle();
		         Calendar cal = Calendar.getInstance();
					int curYear = cal.get(Calendar.YEAR);
					int curMonth = cal.get(Calendar.MONTH);
					curMonth++;
					int curDate = cal.get(Calendar.DATE);
		         try {
		       
		        String Doctors_d_g=combo_box.getValue().toString();
		        String app_dates_g=datePicker.getValue().toString();			       
		        String app_time_g=t.getText(); 
		        String dept_g = "GYNOCOLOGIST";
		        Alert noti = new Alert(AlertType.NONE);
		        if(datePicker.getValue().getYear()>=curYear && datePicker.getValue().getMonthValue()>=curMonth &&datePicker.getValue().getDayOfMonth()>=curDate) {
		        	//register for JDBC drive
		        	 Class.forName("com.mysql.cj.jdbc.Driver");
		        	 //open a connection 
		        	 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
		        	 //execute a query
		        	 stm = conn.createStatement();
		        	 String app;
		        	 int count=0;
		        	 String checkquery="SELECT *from appointment WHERE appointment_date='"+ app_dates_g+"'and dept='"+dept_g+"' and eid='"+emid+"'";//add email
		        	 ResultSet chk=conn.createStatement().executeQuery(checkquery);
		        	 if(chk.next()) {
		        		 Alert noti1 = new Alert(AlertType.NONE);
		        		 noti1.setAlertType(AlertType.ERROR);
				        	noti1.setTitle("WRONG VALUE ENTERED");
							noti1.setHeaderText("ERROR");
							noti1.setContentText("You already have a booking for this date.");
							noti1.showAndWait();
		        	 }
		        	 else {
		        		 String selectquery="SELECT COUNT(*) from appointment WHERE Doctors_detail='"+Doctors_d_g+"'and appointment_date='"+ app_dates_g+"' and appointment_time='"+app_time_g+"' and dept='"+dept_g+"'";
			        	 ResultSet rs=conn.createStatement().executeQuery(selectquery);
			        	 while(rs.next()) {
			        		 count+=rs.getInt("COUNT(*)");
			        	 }	
			        	 
			        	 if(count<15)
			        	 {
			                 app = "INSERT INTO appointment(eid,Doctors_detail,appointment_date,appointment_time,dept)values('"+emid+"','"+Doctors_d_g+"','"+ app_dates_g+"','"+app_time_g+"','"+dept_g+"')";
			                 try {
			    	    		 RadioButton selTg = (RadioButton) tm.getSelectedToggle();
			    	    		 String tmg = selTg.getText();
			    			selected1.setText("\t\tYour appointment with : \t\t\n\n "+"DOCTOR  :  "+combo_box.getValue()
			    	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmg+"\n is booked for the time slot of 2-HOURS"
			    	    			);
			    	    	 ro2.add(selected1,0,180);
			    	           final String value=(String) combo_box.getValue();
			    	     } catch(Exception eh) {System.out.println(eh);}
			        	     stm.execute(app);
			        	 }
			        	 else {
			        		 RadioButton selTg = (RadioButton) tm.getSelectedToggle();
		    	    		 String tmg = selTg.getText();
			        		 selected1.setText("\t\tSORRY Your appointment with : \t\t\n "+"DOCTOR  :  "+combo_box.getValue()
	   	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmg+"\n cannot be booked");
			        	 }
			        
			        	 stm.close();
		        	 }
		        	 
		        }
		        else
		        {
		        	noti.setAlertType(AlertType.ERROR);
		        	noti.setTitle("WRONG VALUE ENTERED");
					noti.setHeaderText("ERROR");
					noti.setContentText("You can not book an appointment for the selected date.Please select a valid date.");
					noti.showAndWait();
		        }
		        	 
		         }
		        	 catch(ClassNotFoundException er){
                        //Handle errors for Class.forName
                        System.out.println(er);
                     }
                   catch(SQLException se)
                    {           
                   	 System.out.println(se);
                   		}
        	
	         });
	     // Create a scene 
		     Scene scene = new Scene(ro2); 
		     Stage secondStage = new Stage();
		     secondStage.setMaximized(true);
	
		     Scene secondscene = null;
				secondStage.setScene(scene); // set the scene
				secondStage.setTitle("ABS Hospital - Appointment Booking"); 
				
	            secondStage.show();
	            primarystage.close();
	           
	    		ro2.setBackground(bg);
	    		 
		 });
	    
submitButton5.setOnAction(e-> {
    
	 // Set title for the stage 
    primarystage.setTitle("ABS Hospital - Appointment Booking"); 
    GridPane ro3 = new GridPane(); 
    ro3.setHgap(1);
    ro3.setVgap(2);
    ro3.setMinSize(800, 750);
	 ro3.setPadding(new Insets(10,5,10,10));
	 ro3.setAlignment(Pos.TOP_CENTER);
       
	 //TITLE
	 String url3="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
	 Image hc3 = new Image (url3,80,0,true,true);
	 ImageView hcn3 = new ImageView(hc3);
	 Label pd3 = new Label ("ABS Hospital",hcn3);
	 pd3.setFont(new Font("Copperplate Gothic Light",60));
	 ro3.add(pd3, 0, 0);
	 //SEPARATOR AFTER THE TITLE
	 Separator sep3 = new Separator();
	 sep3.setPrefSize(250,20);
	 ro3.add(sep3, 0, 1);
	 

	 Label bk3 = new Label ("BOOK  YOUR  APPOINTMENT  : ");
	 bk3.setFont(new Font("Copperplate Gothic Light",30));
	 ro3.add(bk3,0, 24); 
  
	 Label Nds = new Label ("NEUROLOGIST DOCTORS   :");
	 Nds.setFont(new Font("Copperplate Gothic Light",20));
	 ro3.add(Nds,0, 65); 
      // doctors
     
    String doctors[] = 
               { "Dr.SIMON-NEUROLOGIST",
                 "Dr.RADHIKA-NEUROLOGIST",
                 "Dr.RISHIKA-NEUROLOGIST",
                 "Dr.KHANNA-NEUROLOGIST"}; 

    // Create a combo box 
     ComboBox combo_box = new ComboBox(FXCollections .observableArrayList(doctors)); 
      
  // DATE
		Label date3 = new Label ("   DATE : ");
		date3.setFont(new Font("Copperplate Gothic Light",18));
		ro3.add(date3, 0, 80);
		DatePicker datePicker = new DatePicker();
		ro3.add(datePicker, 1, 80);
        
		//timings
		Label time3 = new Label("TIMINGS: ");
		time3.setFont(new Font(18));
		ro3.add(time3, 0,95);
		RadioButton m = new RadioButton("\t09:00:00\t");
		RadioButton a = new RadioButton("\t12:00:00\t");
		RadioButton o = new RadioButton("\t03:00:00\t");
		ToggleGroup tm = new ToggleGroup();
		m.setToggleGroup(tm);
		a.setToggleGroup(tm);
		o.setToggleGroup(tm);
		HBox timen1 = new HBox();
		timen1.setPadding(new Insets(10, 10, 10, 10));
	    timen1.setSpacing(10);
	    timen1.getChildren().addAll(m, a, o);
		ro3.add(timen1,1, 95);
	
     //APPOINTMENT STATUS
     ro3.add(combo_box,1,65);
     Label ST3 = new Label (" APPOINTMENT STATUS : ");
		 ST3.setFont(new Font("Copperplate Gothic Light",30));
		 ro3.add(ST3,0,150); 
    // Label to display the selected menu item 
     Label selected1 = new Label("\t\t\tDoctors appointment not yet booked"); 
     selected1.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 20));
     ro3.add(selected1,0,180);

    Button OKButton = new Button("CONFIRM APPOINTMENT");
    OKButton.setPrefSize(250,50);
    OKButton.setFont(new Font("Century Gothic",18));
    OKButton.setStyle("-fx-base:  #7ecece");
    ro3.add(OKButton,3,200);
    
    OKButton.setOnAction(e1->{
        
   	 Connection conn = null;
        Statement stm = null;
       RadioButton t=(RadioButton)tm.getSelectedToggle();
       Calendar cal = Calendar.getInstance();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH);
		curMonth++;
		int curDate = cal.get(Calendar.DATE);
        try {
      
       String Doctors_d_n=combo_box.getValue().toString();
       String app_dates_n=datePicker.getValue().toString();			       
       String app_time_n=t.getText(); 
       String dept_n = "NEUROLOGIST";
       Alert noti = new Alert(AlertType.NONE);
       if(datePicker.getValue().getYear()>=curYear && datePicker.getValue().getMonthValue()>=curMonth &&datePicker.getValue().getDayOfMonth()>=curDate) {
       	 //register for JDBC drive
      	 Class.forName("com.mysql.cj.jdbc.Driver");
      	 //open a connection 
      	 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
      	 //execute a query
      	 stm = conn.createStatement();
      	 String app;
      	 int count=0;
      	 String checkquery="SELECT *from appointment WHERE appointment_date='"+ app_dates_n+"'and dept='"+dept_n+"' and eid='"+emid+"'";//add email
   	 ResultSet chk=conn.createStatement().executeQuery(checkquery);
   	 if(chk.next()) {
   		 Alert noti1 = new Alert(AlertType.NONE);
   		 noti1.setAlertType(AlertType.ERROR);
	        	noti1.setTitle("WRONG VALUE ENTERED");
				noti1.setHeaderText("ERROR");
				noti1.setContentText("You already have a booking for this date.");
				noti1.showAndWait();
   	 }
   	 else {
   		 String selectquery="SELECT COUNT(*) from appointment WHERE Doctors_detail='"+Doctors_d_n+"'and appointment_date='"+ app_dates_n+"' and appointment_time='"+app_time_n+"' and dept='"+dept_n+"'";
          	 ResultSet rs=conn.createStatement().executeQuery(selectquery);
          	 while(rs.next()) {
          		 count+=rs.getInt("COUNT(*)");
          	 }	
          	    	 if(count<15)
          	 {
                   app = "INSERT INTO appointment(eid,Doctors_detail,appointment_date,appointment_time,dept)values('"+emid+"','"+Doctors_d_n+"','"+ app_dates_n+"','"+app_time_n+"','"+dept_n+"')";
                   try {
      	    		 RadioButton selTn = (RadioButton) tm.getSelectedToggle();
      	    		 String tmn = selTn.getText();
      			selected1.setText("\t\tYour appointment with : \t\t\n\n "+"DOCTOR  :  "+combo_box.getValue()
      	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmn+"\n is booked for the time slot of 2-HOURS"
      	    			);
      	    	 ro3.add(selected1,0,180);
      	           final String value=(String) combo_box.getValue();
      	     } catch(Exception eh) {System.out.println(eh);}
          	     stm.execute(app);
          	 }
          	 else {
          		 RadioButton selTn = (RadioButton) tm.getSelectedToggle();
   	    		 String tmn = selTn.getText();
          		 selected1.setText("\t\tSORRY Your appointment with : \t\t\n "+"DOCTOR  :  "+combo_box.getValue()
      			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmn+"\n cannot be booked");
          	 }
          
          	 stm.close();
   	 }
      	 
       }
       else
       {
       	noti.setAlertType(AlertType.ERROR);
       	noti.setTitle("WRONG VALUE ENTERED");
			noti.setHeaderText("ERROR");
			noti.setContentText("You can not book an appointment for the selected date.Please select a valid date.");
			noti.showAndWait();
       }
       	
        }
       	 catch(ClassNotFoundException er){
                //Handle errors for Class.forName
                System.out.println(er);
             }
           catch(SQLException se)
            {           
           	 System.out.println(se);
           		}
	
    });


        
   // Create a scene 
    Scene scene = new Scene(ro3); 
    Stage secondStage = new Stage();
    secondStage.setMaximized(true);

    Scene secondscene = null;
		secondStage.setScene(scene); // set the scene
		secondStage.setTitle("ABS Hospital - Appointment Booking"); 
		
       secondStage.show();
       primarystage.close();
      
		ro3.setBackground(bg);
	
});

submitButton6.setOnAction(e-> {
	 // Set title for the stage 
		     primarystage.setTitle("ABS Hospital - Appointment Booking"); 
		     GridPane ro4 = new GridPane(); 
		     ro4.setHgap(1);
		     ro4.setVgap(2);
		     ro4.setMinSize(800, 750);
			 ro4.setPadding(new Insets(10,5,10,10));
			 ro4.setAlignment(Pos.TOP_CENTER);
		     
   
	 //TITLE
			 String url4="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
			 Image hc4 = new Image (url4,80,0,true,true);
			 ImageView hcn4 = new ImageView(hc4);
			 Label pd4 = new Label ("ABS Hospital",hcn4);
			 pd4.setFont(new Font("Copperplate Gothic Light",60));
			 ro4.add(pd4, 0, 0);
			 //SEPARATOR AFTER THE TITLE
			 Separator sep4 = new Separator();
			 sep4.setPrefSize(250,20);
			 ro4.add(sep4, 0, 1);
			 

			 Label bk4 = new Label ("BOOK  YOUR  APPOINTMENT  : ");
			 bk4.setFont(new Font("Copperplate Gothic Light",30));
			 ro4.add(bk4,0, 24); 
  
			 Label gpd = new Label ("GENERAL PHYSCIANS   :");
			 gpd.setFont(new Font("Copperplate Gothic Light",20));
			 ro4.add(gpd,0, 65); 
		  
    // doctors
  
		     String doctors[] = 
		                { "Dr.SHEKHAR-GENERAL PHYSCIANS ",
		                  "Dr.NAIDU-GENERAL PHYSCIANS",
		                  "Dr.PREM-GENERAL PHYSCIANS",
		                  "Dr.REKHA-GENERAL PHYSCIANS"}; 

    // Create a combo box 
		     ComboBox combo_box = new ComboBox(FXCollections .observableArrayList(doctors)); 
      
  // DATE
	 		Label date4 = new Label ("   DATE : ");
	 		date4.setFont(new Font("Copperplate Gothic Light",18));
	 		ro4.add(date4, 0, 80);
	 		DatePicker datePicker = new DatePicker();
	 		ro4.add(datePicker, 1, 80);
        
		//timings
			Label time4 = new Label("TIMINGS: ");
			time4.setFont(new Font(18));
			ro4.add(time4, 0,95);
			RadioButton m = new RadioButton("\t09:00:00\t");
			RadioButton a = new RadioButton("\t12:00:00\t");
			RadioButton o = new RadioButton("\t03:00:00\t");
			ToggleGroup tm = new ToggleGroup();
			m.setToggleGroup(tm);
			a.setToggleGroup(tm);
			o.setToggleGroup(tm);
			HBox timee4 = new HBox();
			timee4.setPadding(new Insets(10, 10, 10, 10));
		    timee4.setSpacing(10);
		    timee4.getChildren().addAll(m, a, o);
			ro4.add(timee4,1, 95);
	
     //APPOINTMENT STATUS
			ro4.add(combo_box,1,65);
			Label ST4 = new Label (" APPOINTMENT STATUS : ");
		    ST4.setFont(new Font("Copperplate Gothic Light",30));
		    ro4.add(ST4,0,150); 
    // Label to display the selected menu item 
		      Label selected1 = new Label("\t\t\tDoctors appointment not yet booked"); 
		      selected1.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 20));
		      ro4.add(selected1,0,180);

		     Button OKButton = new Button("CONFIRM APPOINTMENT");
		     OKButton.setPrefSize(250,50);
		     OKButton.setFont(new Font("Century Gothic",18));
		     OKButton.setStyle("-fx-base:  #7ecece");
		     ro4.add(OKButton,3,200);
		     
		     OKButton.setOnAction(e1->{
		         
	    	 Connection conn = null;
	         Statement stm = null;
	         RadioButton t=(RadioButton)tm.getSelectedToggle();
	         Calendar cal = Calendar.getInstance();
	 		int curYear = cal.get(Calendar.YEAR);
	 		int curMonth = cal.get(Calendar.MONTH);
	 		curMonth++;
	 		int curDate = cal.get(Calendar.DATE);
	         try {
      
	        String Doctors_d_gp=combo_box.getValue().toString();
	        String app_dates_gp=datePicker.getValue().toString();			       
	        String app_time_gp=t.getText(); 
	        String dept_gp = "GENERAL PHYSCIANS";
	        Alert noti = new Alert(AlertType.NONE);
	        if(datePicker.getValue().getYear()>=curYear && datePicker.getValue().getMonthValue()>=curMonth &&datePicker.getValue().getDayOfMonth()>=curDate) {
	        	//register for JDBC drive
	        	 Class.forName("com.mysql.cj.jdbc.Driver");
	        	 //open a connection 
	        	 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
	        	 //execute a query
	        	 stm = conn.createStatement();
	        	 String app;
	        	 int count=0;
	        	 String checkquery="SELECT *from appointment WHERE appointment_date='"+ app_dates_gp+"'and dept='"+dept_gp+"' and eid='"+emid+"'";//add email
	        	 ResultSet chk=conn.createStatement().executeQuery(checkquery);
	        	 if(chk.next()) {
	        		 Alert noti1 = new Alert(AlertType.NONE);
	        		 noti1.setAlertType(AlertType.ERROR);
	    	        	noti1.setTitle("WRONG VALUE ENTERED");
	    				noti1.setHeaderText("ERROR");
	    				noti1.setContentText("You already have a booking for this date.");
	    				noti1.showAndWait();
	        	 }
	        	 else {
	        		 String selectquery="SELECT COUNT(*) from appointment WHERE Doctors_detail='"+Doctors_d_gp+"'and appointment_date='"+ app_dates_gp+"' and appointment_time='"+app_time_gp+"' and dept='"+dept_gp+"'";
		        	 ResultSet rs=conn.createStatement().executeQuery(selectquery);
		        	 while(rs.next()) {
		        		 count+=rs.getInt("COUNT(*)");
		        	 }	
		        	 
		        	 if(count<15)
		        	 {
		                 app = "INSERT INTO appointment(eid,Doctors_detail,appointment_date,appointment_time,dept)values('"+emid+"','"+Doctors_d_gp+"','"+ app_dates_gp+"','"+app_time_gp+"','"+dept_gp+"')";
		                 try {
		    	    		 RadioButton selTgp = (RadioButton) tm.getSelectedToggle();
		    	    		 String tmgp = selTgp.getText();
		    			selected1.setText("\t\tYour appointment with : \t\t\n\n "+"DOCTOR  :  "+combo_box.getValue()
		    	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmgp+"\n is booked for the time slot of 2-HOURS"
		    	    			);
		    	    	 ro4.add(selected1,0,180);
		    	           final String value=(String) combo_box.getValue();
		    	              } catch(Exception eh) {System.out.println(eh);}
		        	            stm.execute(app);
		        	 }
		        	 else {
		        		 RadioButton selTgp = (RadioButton) tm.getSelectedToggle();
			    		 String tmgp = selTgp.getText();
		        		 selected1.setText("\t\tSORRY Your appointment with : \t\t\n "+"DOCTOR  :  "+combo_box.getValue()
		    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmgp+"\n cannot be booked");
		        	      }
		        
		        	 stm.close();
	        	 }
	        	 
	        }
	        else
	        {
	        	noti.setAlertType(AlertType.ERROR);
	        	noti.setTitle("WRONG VALUE ENTERED");
				noti.setHeaderText("ERROR");
				noti.setContentText("You can not book an appointment for the selected date.Please select a valid date.");
				noti.showAndWait();
	        }
       	 
        }
       	 catch(ClassNotFoundException er){
                //Handle errors for Class.forName
                System.out.println(er);
             }
           catch(SQLException se){           
           	 System.out.println(se);
             }
	
    });
         
      // Create a scene 
		     Scene scene = new Scene(ro4); 
		     Stage secondStage = new Stage();
		     secondStage.setMaximized(true);
		     secondStage.setScene(scene); // set the scene
			 secondStage.setTitle("ABS Hospital - Appointment Booking"); 	
		     secondStage.show();
		     primarystage.close();
		     ro4.setBackground(bg);
	 });

submitButton7.setOnAction(e-> {
	 // Set title for the stage 
		     primarystage.setTitle("ABS Hospital - Appointment Booking"); 
		     GridPane ro5 = new GridPane(); 
		     ro5.setHgap(1);
		     ro5.setVgap(2);
		     ro5.setMinSize(800, 750);
			 ro5.setPadding(new Insets(10,5,10,10));
			 ro5.setAlignment(Pos.TOP_CENTER);
		        
	 //TITLE
			 String url5="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
			 Image hc5 = new Image (url5,80,0,true,true);
			 ImageView hcn5 = new ImageView(hc5);
			 Label pd5 = new Label ("ABS Hospital",hcn5);
			 pd5.setFont(new Font("Copperplate Gothic Light",60));
			 ro5.add(pd5, 0, 0);
	 //SEPARATOR AFTER THE TITLE
			 Separator sep5 = new Separator();
			 sep5.setPrefSize(250,20);
			 ro5.add(sep5, 0, 1);
			 
			 Label bk5 = new Label ("BOOK  YOUR  APPOINTMENT  : ");
			 bk5.setFont(new Font("Copperplate Gothic Light",30));
			 ro5.add(bk5,0, 24); 
		   
			 Label od = new Label ("ORTHOPEDIC DOCTORS  :");
			 od.setFont(new Font("Copperplate Gothic Light",20));
			 ro5.add(od,0, 65); 
  
    // doctors
		      String doctors[] = 
		                { "Dr.STEPHEN-ORTHOPEDIC DOCTORS ",
		                  "Dr.PARTH-ORTHOPEDIC DOCTORS",
		                  "Dr.ALI-ORTHOPEDIC DOCTORS",
		                  "Dr.HARSHINI-ORTHOPEDIC DOCTORS"}; 

    // Create a combo box 
     		ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(doctors)); 
      
  // DATE
	 		Label date5 = new Label ("   DATE : ");
	 		date5.setFont(new Font("Copperplate Gothic Light",18));
	 		ro5.add(date5, 0, 80);
	 		DatePicker datePicker = new DatePicker();
	 		ro5.add(datePicker, 1, 80);
	         
	//timings
			Label time5 = new Label("TIMINGS: ");
			time5.setFont(new Font(18));
			ro5.add(time5, 0,95);
			RadioButton m = new RadioButton("\t09:00:00\t");
			RadioButton a = new RadioButton("\t12:00:00\t");
			RadioButton o = new RadioButton("\t03:00:00\t");
			ToggleGroup tm = new ToggleGroup();
			m.setToggleGroup(tm);
			a.setToggleGroup(tm);
			o.setToggleGroup(tm);
			HBox timee5 = new HBox();
			timee5.setPadding(new Insets(10, 10, 10, 10));
		    timee5.setSpacing(10);
		    timee5.getChildren().addAll(m, a, o);
			ro5.add(timee5,1, 95);
		
     //APPOINTMENT STATUS
            ro5.add(combo_box,1,65);
            Label ST5 = new Label (" APPOINTMENT STATUS : ");
		     ST5.setFont(new Font("Copperplate Gothic Light",30));
		     ro5.add(ST5,0,150); 
    // Label to display the selected menu item 
            Label selected1 = new Label("\t\t\tDoctors appointment not yet booked"); 
            selected1.setFont(Font.font("Copperplate Gothic Light", FontWeight.BOLD, FontPosture.REGULAR, 20));
            ro5.add(selected1,0,180);

            Button OKButton = new Button("CONFIRM APPOINTMENT");
            OKButton.setPrefSize(250,50);
            OKButton.setFont(new Font("Century Gothic",18));
            OKButton.setStyle("-fx-base:  #7ecece");
            ro5.add(OKButton,3,200);
    
    OKButton.setOnAction(e1->{
        
   	 Connection conn = null;
        Statement stm = null;
        RadioButton t=(RadioButton)tm.getSelectedToggle();
        Calendar cal = Calendar.getInstance();
	 		int curYear = cal.get(Calendar.YEAR);
	 		int curMonth = cal.get(Calendar.MONTH);
	 		curMonth++;
	 		int curDate = cal.get(Calendar.DATE);
        try {
            String Doctors_d_o=combo_box.getValue().toString();
            String app_dates_o=datePicker.getValue().toString();			       
            String app_time_o=t.getText(); 
            String dept_o = "ORTHOPEDIC";
            Alert noti = new Alert(AlertType.NONE);
	        if(datePicker.getValue().getYear()>=curYear && datePicker.getValue().getMonthValue()>=curMonth &&datePicker.getValue().getDayOfMonth()>=curDate) {
	        	//register for JDBC drive
	        	 Class.forName("com.mysql.cj.jdbc.Driver");
	        	 //open a connection 
	        	 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
	        	 //execute a query
	        	 stm = conn.createStatement();
	        	 String app;
	        	 int count=0;
	        	String checkquery="SELECT *from appointment WHERE appointment_date='"+ app_dates_o+"'and dept='"+dept_o+"' and eid='"+emid+"'";//add email
	        	 ResultSet chk=conn.createStatement().executeQuery(checkquery);
	        	 if(chk.next()) {
	        		 Alert noti1 = new Alert(AlertType.NONE);
	        		 noti1.setAlertType(AlertType.ERROR);
	    	        	noti1.setTitle("WRONG VALUE ENTERED");
	    				noti1.setHeaderText("ERROR");
	    				noti1.setContentText("You already have a booking for this date.");
	    				noti1.showAndWait();
	        	 }
	        	 else {
	        		 String selectquery="SELECT COUNT(*) from appointment WHERE Doctors_detail='"+Doctors_d_o+"'and appointment_date='"+ app_dates_o+"' and appointment_time='"+app_time_o+"' and dept='"+dept_o+"'";
	 	        	 ResultSet rs=conn.createStatement().executeQuery(selectquery);
	 	        	 while(rs.next()) {
	 	        		 count+=rs.getInt("COUNT(*)");
	 	        	 }	
	 	        	  	 if(count<15)
	 	        	 {
	 	                 app = "INSERT INTO appointment(eid,Doctors_detail,appointment_date,appointment_time,dept)values('"+emid+"','"+Doctors_d_o+"','"+ app_dates_o+"','"+app_time_o+"','"+dept_o+"')";
	 	                 try {
	 	    	    		 RadioButton selTo = (RadioButton) tm.getSelectedToggle();
	 	    	    		 String tmo = selTo.getText();
	 	    			selected1.setText("\t\tYour appointment with : \t\t\n\n "+"DOCTOR  :  "+combo_box.getValue()
	 	    	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmo+"\n is booked for the time slot of 2-HOURS"
	 	    	    			);
	 	    	    	       ro5.add(selected1,0,180);
	 	    	               final String value=(String) combo_box.getValue();
	 	    	             } catch(Exception eh) {System.out.println(eh);}
	 	        	           stm.execute(app);
	 	        	 }
	 	        	 else {
	 	        		 RadioButton selTo = (RadioButton) tm.getSelectedToggle();
	 		    		 String tmo = selTo.getText();
	 	        		 selected1.setText("\t\tSORRY Your appointment with : \t\t\n "+"DOCTOR  :  "+combo_box.getValue()
	 	    			 +"\t\t\nDATE : "+ datePicker.getValue()+"\t\t\nTIME : "+tmo+"\n cannot be booked");
	 	        	      }
	 	        
	 	        	 stm.close();
	        	 }
	        	
	        }
	       else
	        {
	        	noti.setAlertType(AlertType.ERROR);
	        	noti.setTitle("WRONG VALUE ENTERED");
				noti.setHeaderText("ERROR");
				noti.setContentText("You can not book an appointment for the selected date.Please select a valid date.");
				noti.showAndWait();
	        }
       	 
            }
       	 catch(ClassNotFoundException er){
                //Handle errors for Class.forName
                System.out.println(er);
             }
           catch(SQLException se){           
           	 System.out.println(se);
             }
	
    });
        
    	// Create a scene 
    Scene scene = new Scene(ro5); 
    Stage secondStage = new Stage();
    secondStage.setMaximized(true);
    secondStage.setScene(scene); // set the scene
    secondStage.setTitle("ABS Hospital - Appointment Booking"); 
    secondStage.show();
    primarystage.close();
    ro5.setBackground(bg);
		 
});
	      // Create a scene 
	     Scene scene = new Scene(root);
	    // Set the scene 
	     primarystage.setScene(scene); 
	     primarystage.setMaximized(true);
	     primarystage.show(); 
	 }
    
	
	/*To Open Personal Profile*/
	public void showEditProfile(String emid) {
		Stage myStage = new Stage();
		myStage.setTitle("ABS Patient Details");
		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setMinSize(800,750);
		gp.setPadding(new Insets(10,10,10,10));
		gp.setAlignment(Pos.CENTER);
		Image image = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg"); 
        BackgroundImage backgroundimage = new BackgroundImage(image,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundPosition.CENTER,  
                                             BackgroundSize.DEFAULT); 
        Background background = new Background(backgroundimage); 
		gp.setBackground(background);
		
		String url="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
		Image hc = new Image (url,50,50,true,true);
		ImageView hcn = new ImageView(hc);
		Label pd = new Label ("PERSONAL DETAILS",hcn);
		pd.setFont(new Font("Copperplate Gothic Light",42));
		gp.add(pd, 0, 0);
		
		Separator sep = new Separator();
		sep.setPrefSize(250,20);
		gp.add(sep, 0, 1);
		
		//FIRST NAME
		Label fn = new Label("First Name: ");
		fn.setFont(new Font(18));
		gp.add(fn, 0, 2);
		TextField fntf = new TextField();
		gp.add(fntf, 1, 2);
		
		//LAST NAME
		Label ln = new Label("Last Name: ");
		ln.setFont(new Font(18));
		gp.add(ln, 0, 3);
		TextField lntf = new TextField();
		gp.add(lntf, 1, 3);
		
		//BIRTH DATE
		Label dob = new Label ("D.O.B: ");
		dob.setFont(new Font(18));
		gp.add(dob, 0, 4);
		DatePicker dobPicker = new DatePicker();
		Tooltip tp = new Tooltip("Click enter if date is entered manually");
		dobPicker.setTooltip(tp);
		gp.add(dobPicker, 1, 4);
		
		//AGE
		Label age = new Label("Age: ");
		age.setFont(new Font(18));
		gp.add(age, 0, 5);
		TextField ageDef = new TextField();
		gp.add(ageDef, 1, 5);
		dobPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				int ageOfP;
				Calendar cal = Calendar.getInstance();
				int curYear = cal.get(Calendar.YEAR);
				int curMonth = cal.get(Calendar.MONTH);
				curMonth++;
				int curDate = cal.get(Calendar.DATE);
				ageOfP=(curYear-dobPicker.getValue().getYear());
				ageOfP--;
				if(curDate >= dobPicker.getValue().getDayOfMonth() && (curMonth) >= dobPicker.getValue().getMonthValue())
					ageOfP++;
				ageDef.setText(Integer.toString(ageOfP));
				ageDef.setDisable(true);
			}
		});
		
		
		//SEX
		Label gen = new Label("Sex: ");
		gen.setFont(new Font(18));
		gp.add(gen, 0, 6);
		RadioButton m = new RadioButton("M");
		RadioButton f = new RadioButton("F");
		RadioButton o = new RadioButton("Transgender");
		ToggleGroup tgGen = new ToggleGroup();
		m.setToggleGroup(tgGen);
		f.setToggleGroup(tgGen);
		o.setToggleGroup(tgGen);
		HBox genb = new HBox();
		genb.setPadding(new Insets(10, 10, 10, 10));
	    genb.setSpacing(10);
	    genb.getChildren().addAll(m, f, o);
		gp.add(genb, 1, 6);
		
		//RELATIONSHIP STATUS
		Label rs = new Label("Relationship Status: ");
		rs.setFont(new Font(18));
		gp.add(rs, 0, 7);
		ComboBox rsCB = new ComboBox(FXCollections.observableArrayList("Single", "Partnered", "Married", "Separated","Divorced","Widowed"));
		gp.add(rsCB, 1, 7);
		
		//EMERGENCY CONTACT
		Label ec = new Label("Emergency Contact: ");
		ec.setFont(new Font(18));
		gp.add(ec, 0, 8);
		TextField cnum = new TextField();
		gp.add(cnum, 1, 8);
		
		Label phh = new Label("\nPERSONAL HEALTH");
		phh.setFont(new Font("Copperplate Gothic Light",20));
		phh.setUnderline(true);
		gp.add(phh, 0, 9);
		
		//BLOOD GROUP
		Label blgp = new Label("Blood Group: ");
		blgp.setFont(new Font(18));
		gp.add(blgp, 0, 10);
		ComboBox bgcb = new ComboBox(FXCollections.observableArrayList("A+", "A-", "B+", "B-","O+","O-","AB+","AB-"));
		gp.add(bgcb, 1, 10);
		
		//CHILDHOOD ILLNESS
		Label chIll = new Label("Childhood Illness: ");
		chIll.setFont(new Font(18));
		gp.add(chIll,0,11);
		CheckBox d1 = new CheckBox("Measles"); gp.add(d1, 1, 11); 
		CheckBox d2 = new CheckBox("Mumps"); gp.add(d2, 1, 12);
		CheckBox d3 = new CheckBox("Rubella"); gp.add(d3, 1, 13);
		CheckBox d4 = new CheckBox("Chickenpox"); gp.add(d4, 1, 14);
		CheckBox d5 = new CheckBox("Polio"); gp.add(d5, 1, 15);
		CheckBox d6 = new CheckBox("Rheumatic Fever"); gp.add(d6, 1, 16);
		
		//OTHER MEDICAL PROBLEMS
		Label mp = new Label("Medical Problems: ");
		mp.setFont(new Font(18));
		gp.add(mp, 0, 17);
		TextField mptb = new TextField();
		mptb.setPromptText("Mention NONE if there isn't any");
		gp.add(mptb, 1, 17);
		
		//SUBMIT AND CLEAR
		Button submit = new Button("Submit");
		submit.setFont(new Font("Century Gothic",13));
        submit.setPrefSize(100,35);
        submit.setStyle("-fx-base: #7ecece;");
		Button clear = new Button("Clear");
		clear.setFont(new Font("Century Gothic",13));
        clear.setPrefSize(100,35);
        clear.setStyle("-fx-base: #7ecece;");
		
		Alert a = new Alert(AlertType.NONE);
		//EVENT HANDLER FOR SUBMIT 
		submit.setOnAction(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent ae) {
				if(fntf.getText().isEmpty()) {//first name field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+fn.getText()+" cannot be empty");
					a.showAndWait();
				}
				else if(lntf.getText().isEmpty()) {//last name field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+ln.getText()+" cannot be empty");
					a.showAndWait();
				}
				else if(dobPicker.getValue()==null) {//dob field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+dob.getText()+" cannot be empty");
					a.showAndWait();
				}
				else if(tgGen.getSelectedToggle() == null) {//sex field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+gen.getText()+" cannot be empty");
					a.showAndWait();
				}
				else if(rsCB.getValue() == null) {//relationship status field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+rs.getText()+" cannot be empty");
					a.showAndWait();
				}
				else if(cnum.getText().isEmpty()) {//emergency contact field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+ec.getText()+" cannot be empty");
					a.showAndWait();
				}
				else if(bgcb.getValue() == null) {//blood group field empty
					a.setAlertType(AlertType.ERROR);
					a.setTitle("Field Blank");
					a.setHeaderText("ERROR");
					a.setContentText("The field "+blgp.getText()+" cannot be empty");
					a.showAndWait();
				}
				else {
					//entering data into the database
					
						try{
		                   	 Class.forName("com.mysql.cj.jdbc.Driver");
		                   	 Connection conPD=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
		                   	 
		                   	 first_name=fntf.getText();										
		                   	 last_name=lntf.getText();										
		                   	 birth_date = dobPicker.getValue().toString();					
		                   	 ageOfPatient = ageDef.getText();								
		                   	 RadioButton Selsex = (RadioButton) tgGen.getSelectedToggle();
		                   	 sex=Selsex.getText();											
		                   	 relationship_status = rsCB.getValue().toString();				
		                   	 emergency_phno = cnum.getText();								
		                   	 blood_group = bgcb.getValue().toString();
		                     //Storing all the selected check box's values in a String
		             		if (d1.isSelected()) childhood_Illness+="|Measles|";
		             		if (d2.isSelected()) childhood_Illness+="|Mumps|";
		             		if (d3.isSelected()) childhood_Illness+="|Rubella|";
		             		if (d4.isSelected()) childhood_Illness+="|Chickenpox|";
		             		if (d5.isSelected()) childhood_Illness+="|Polio|";
		             		if (d6.isSelected()) childhood_Illness+="|Rheumatic Fever|";
		             		childhood_Illness+="]";
		             		if(childhood_Illness.length()<5) childhood_Illness="None";
		                   	if(mptb.getText()!=null) MedicalProblems = mptb.getText();		                
		                   	
		                   	String EP="INSERT INTO Patient_Details VALUES ('"+first_name+"','"+last_name+"','"+birth_date+"','"+ageOfPatient+"','"+sex+"','"+relationship_status+"','"+emergency_phno+"','"+blood_group+"','"+childhood_Illness+"','"+MedicalProblems+"','"+emid+"')"+
		                   	"ON DUPLICATE KEY UPDATE First_name='"+first_name+"',Last_Name='"+last_name+"',DOB='"+birth_date+"',Age='"+ageOfPatient+"',Sex='"+sex+"',Relationship_Status='"+relationship_status+"',SOS_Contact='"+emergency_phno+"',BloodGroup='"+blood_group+"',"+
		                   	"Childhood_Illness='"+childhood_Illness+"',Medical_Problems='"+MedicalProblems+"'";
			                Statement st = conPD.prepareStatement(EP);
			                st.executeUpdate(EP);
	                    }
                    catch(ClassNotFoundException e){
                        //Handle errors for Class.forName
                        e.printStackTrace();
                     }
                    catch(SQLException se)
                    {
                    	se.printStackTrace();
                    }
					a.setAlertType(AlertType.INFORMATION);
					a.setTitle("Submission Details");
					a.setHeaderText("ALERT");
					a.setContentText("Personal details has been updated successfully");
					a.showAndWait();
					//clearing after the data has been updated
					fntf.clear();
					lntf.clear();
					dobPicker.getEditor().clear();
					ageDef.setDisable(false);
					ageDef.clear();
					tgGen.selectToggle(null);
					rsCB.setValue(null);
					cnum.clear();
					bgcb.setValue(null);
					d1.setSelected(false);
					d2.setSelected(false);
					d3.setSelected(false);
					d4.setSelected(false);
					d5.setSelected(false);
					d6.setSelected(false);
					mptb.clear();
					myStage.close();
				}
			}
		});
		
		//EVENT HANDLER FOR CLEAR
		clear.setOnAction(new EventHandler <ActionEvent> () {
			public void handle(ActionEvent ae) {
				fntf.clear();
				lntf.clear();
				dobPicker.getEditor().clear();
				ageDef.setDisable(false);
				ageDef.clear();
				tgGen.selectToggle(null);
				rsCB.setValue(null);
				cnum.clear();
				bgcb.setValue(null);
				d1.setSelected(false);
				d2.setSelected(false);
				d3.setSelected(false);
				d4.setSelected(false);
				d5.setSelected(false);
				d6.setSelected(false);
				mptb.clear();
			}
		});
		
		HBox cb = new HBox();
		cb.setPadding(new Insets(10, 10, 10, 10));
	    cb.setSpacing(10);
	    cb.getChildren().addAll(submit,clear);
		gp.add(cb, 0, 18);
		
		Scene myScene = new Scene(gp);
		myStage.setMaximized(true);
		myStage.setScene(myScene);
		myStage.show();
	}
	private TableView table;
	private ObservableList<ObservableList> data;
	public void buildData(String s,String EMAIL)
	{
		data=FXCollections.observableArrayList();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
          	Connection c1=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
			//SQL for selecting ReportId,Date,DoctorConsulted from Report table
			String SQL="SELECT ReportId,DocName,ReportDate FROM report"+" WHERE Department='"+s+"' AND eid='"+EMAIL+"'";//ADD EMAIL
			ResultSet rs=c1.createStatement().executeQuery(SQL);
			
			/*TO add table column dynamically*/
			  
	            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
	                //We are using non property style for making dynamic table
	                final int j = i;
	                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
	                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
	                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	                        return new SimpleStringProperty(param.getValue().get(j).toString());
	                    }
	                });
	               
	                table.getColumns().addAll(col);
	                 }//end of for loop
			
			//Adding data to observable list
			
			while(rs.next()) {
				 ObservableList<String> row = FXCollections.observableArrayList();
	                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	                    //Iterate Column
	                    row.add(rs.getString(i));
	                }
	               data.add(row);

			}
			//adding to table view
			
			table.setItems(data);
		}catch(Exception e ){
			e.printStackTrace();
			System.out.println("Error on building data");
		}
	}
	void Report(String emid) {
		Stage Report = new Stage();
		Report.setTitle("Reports");
		GridPane rN= new GridPane();
		rN.setHgap(10);
		rN.setVgap(10);
		rN.setMinSize(700, 700);
		rN.setPadding(new Insets(10,10,10,10)); //margins around the whole grid
		rN.setAlignment(Pos.CENTER);
		Image image1 = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg"); 
        BackgroundImage backgroundimage1 = new BackgroundImage(image1,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundPosition.CENTER,  
                                             BackgroundSize.DEFAULT); 
        Background background = new Background(backgroundimage1); 
		rN.setBackground(background);

		//creating label- REPORTS
		Label T1=new Label("REPORTS ");
		T1.setFont(new Font("Copperplate Gothic Light",42));
		rN.add(T1, 0, 0);
		//Creating Separator
		Separator S=new Separator();
		S.setPrefSize(250, 20);
		rN.add(S,0,1);
		//Creating Hbox to group the label,listview and 
		HBox hbox=new HBox();
		hbox.setSpacing(50);
				
		Label T2=new Label("Select the department of your choice\nPlease select only one department.");
		T2.setFont(new Font("Copperplate Gothic Light",20));
		//rN.add(T2, 0, 3);
		
		ObservableList<String> DepartmentTypes=FXCollections.observableArrayList("ENT Specialist","Pediatrician","Gynocologist","Neurologist","General Physician","Orthopedic");
		ListView<String> dept=new ListView<String>(DepartmentTypes);
		dept.setPrefSize(200,100);
		dept.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
						
		Button Button_Submit = new Button("Submit");
        Button_Submit.setFont(new Font("Century Gothic",18));
        Button_Submit.setPrefSize(100,45);
        Button_Submit.setStyle("-fx-base: #7ecece;");
        Button_Submit.setTooltip(new Tooltip("Click to view the list of Reports"));
        
        hbox.getChildren().addAll(T2,dept,Button_Submit);
        hbox.setAlignment(Pos.CENTER);
        rN.add(hbox, 0, 3);
        
        final Label l1=new Label("List of your reports");
		l1.setFont(new Font("Century Gothic",20));
      //creating a table
		 table=new TableView();
        //Action when button is clicked
        Button_Submit.setOnAction(e->{
        	ObservableList<String> D =dept.getSelectionModel().getSelectedItems();
        	String list="";
        	for(String m:D) {
        		list+=m+"";
        	}
        	String selection= list;
        	buildData(selection,emid);
        	
        }
        );
        table.setMaxWidth(275);
		final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(l1, table);
        vbox.setAlignment(Pos.CENTER);
        rN.add(vbox,0,5);
              
		
		Label T3=new Label("Enter the report id to view the Report details");
		T3.setFont(new Font("Copperplate Gothic Light",20));
		
		TextField Reptxt=new TextField();
		
		Button RepSubmit= new Button("Submit");
		RepSubmit.setFont(new Font("Century Gothic",18));
		RepSubmit.setPrefSize(100,40);
		RepSubmit.setStyle("-fx-base: #7ecece;");
        RepSubmit.setTooltip(new Tooltip("Click to view Report"));
        
		final HBox vbox2 = new HBox();
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.getChildren().addAll(T3,Reptxt,RepSubmit);
        
        rN.add(vbox2,0,7);

        Alert RepAl = new Alert(AlertType.NONE);
		RepSubmit.setOnAction(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent ae) {
				if(Reptxt.getText().isEmpty()) {//first name field empty
					RepAl.setAlertType(AlertType.ERROR);
					RepAl.setTitle("Field Blank");
					RepAl.setHeaderText("ERROR");
					RepAl.setContentText("This field cannot be empty");
					RepAl.showAndWait();
				}
				else {
					//printing data in another scene 
					showReport(Reptxt.getText(),emid);
					Reptxt.clear();
					
					
				}
			}
		});
	
		Report.setScene(new Scene(rN));
		Report.setMaximized(true);
		Report.show();
	}
	public void showReport(String cond,String EMAIL) {
		Stage RRep=new Stage();
		RRep.setTitle("Report Details");
		GridPane gp2=new GridPane();
		gp2.setVgap(10);
		gp2.setHgap(10);
		gp2.setMinSize(800,750);
		gp2.setPadding(new Insets(10,10,10,10));
		gp2.setAlignment(Pos.CENTER);
		Image image = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg"); 
        BackgroundImage backgroundimage1 = new BackgroundImage(image,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundPosition.CENTER,  
                                             BackgroundSize.DEFAULT); 
        Background background1 = new Background(backgroundimage1); 
		gp2.setBackground(background1);
		//ADDING THE IMAGE AS THE TITLE
		String url1="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
		Image hc1 = new Image (url1,80,0,true,true);
		ImageView hcn1 = new ImageView(hc1);
		Label pd1 = new Label ("ABS Hospital",hcn1);
		pd1.setFont(new Font("Copperplate Gothic Light",60));
		gp2.add(pd1,0,0);
		
		//Separator
		Separator sep1 = new Separator();
		sep1.setPrefSize(250,20);
		gp2.add(sep1, 0, 1);
		
		//Report label
		Label Head=new Label("REPORT");
		Head.setFont(new Font("Copperplate Gothic Light",40));
		Head.setAlignment(Pos.CENTER);
		gp2.add(Head, 0, 2);	
		
		//creating the headings
		Label Lei = new Label("Patient Email Id: "); // Patient Email Id
		Lei.setFont(new Font("Segoe UI Semibold",18));
		
		Label  LRI= new Label("Report Id: ");// Patient's Report ID
		LRI.setFont(new Font("Segoe UI Semibold",20));
		
		Label LPName = new Label("Patient Name: "); //Patient's Name
		LPName.setFont(new Font("Segoe UI Semibold",18));

		Label LDName=new Label("Doctor: "); //Doctor's Name
		LDName.setFont(new Font("Segoe UI Semibold",18));

		Label LRD=new Label("Date: "); //DATE
		LRD.setFont(new Font("Segoe UI Semibold",18));

		Label LDiag=new Label("Diagnosis: "); //Diagnosis
		LDiag.setFont(new Font("Segoe UI Semibold",20));

		Label LTR=new Label("Test Result: "); //Test Result
		LTR.setFont(new Font("Segoe UI Semibold",20));
		
		Label LDep=new Label("Department: "); //Department Consulted
		LDep.setFont(new Font("Segoe UI Semibold",20));
		
		HBox dabba=new HBox();
		dabba.setSpacing(5);
		dabba.setPadding(new Insets(10, 10, 10, 10));
		
		VBox vb1=new VBox();
		vb1.setSpacing(10);
		vb1.setPadding(new Insets(10,10,10,10));
		vb1.getChildren().addAll(LRD,LDName,LDep);
		
		VBox vb2=new VBox();
		vb2.setSpacing(10);
		vb2.setPadding(new Insets(10,10,10,10));
		
		VBox vb3=new VBox();
		vb3.setSpacing(10);
		vb3.setPadding(new Insets(10,10,10,10));
		vb3.getChildren().addAll(Lei ,LPName);
		
		VBox vb4=new VBox();
		vb4.setSpacing(10);
		vb4.setPadding(new Insets(10,10,10,10));
		
		HBox dabba2=new HBox();
		dabba2.setSpacing(10);
		dabba2.setPadding(new Insets(10, 10, 10, 10));
		
		HBox dabba3=new HBox();
		dabba3.setSpacing(10);
		dabba3.setPadding(new Insets(10, 10, 10, 10));
		
		HBox dabba4=new HBox();
		dabba4.setSpacing(20);
		dabba4.setPadding(new Insets(10, 10, 10, 10));
		
		VBox dabba5=new VBox();
		dabba5.setSpacing(20);
		dabba5.setPadding(new Insets(10, 10, 10, 10));
		
		VBox dabba6=new VBox();
		dabba6.setSpacing(20);
		dabba6.setPadding(new Insets(10, 10, 10, 10));
		
		HBox dabba7=new HBox();
		dabba7.setSpacing(20);
		dabba7.setPadding(new Insets(10, 10, 10, 10));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
          	Connection c1=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
			String SQL1="SELECT * FROM report WHERE ReportId='"+cond+"' AND eid='"+EMAIL+"'"; 
			ResultSet rs1=c1.createStatement().executeQuery(SQL1);
			while(rs1.next())
			{
				String eid=rs1.getString("eid");
				String ReportId=rs1.getString("ReportId");
				String PFName=rs1.getString("PFName");
				String PLName=rs1.getString("PLName");
				String DocName=rs1.getString("DocName");
				String ReportDate=rs1.getString("ReportDate");
				String TestResult=rs1.getString("TestResult");
				String Diagnosis=rs1.getString("Diagnosis");
				String Department=rs1.getString("Department");
				
				Label LRI_val=new Label(ReportId); //ReportId value
				Lei.setFont(new Font(20));
				Lei.setContentDisplay(ContentDisplay.CENTER);
				dabba.getChildren().addAll(LRI,LRI_val);
				gp2.add(dabba, 0, 3);
				
				Label Lei_val = new Label(eid);  //Patient Email Id value
				Lei_val.setFont(new Font(20));
				Label LPName_val = new Label(PFName+" "+PLName);// Patient Name value
				LPName_val.setFont(new Font(20));
				vb4.getChildren().addAll(Lei_val ,LPName_val);
				
				Label LDName_val=new Label(DocName); // Doctor Name value
				LDName_val.setFont(new Font(20));
				Label LRD_val=new Label(ReportDate); //Report date value
				LRD_val.setFont(new Font(20));
				Label LDep_val=new Label(Department); //Department Consulted
				LDep_val.setFont(new Font(20));
				vb2.getChildren().addAll(LRD_val,LDName_val,LDep_val);
				
				dabba2.getChildren().addAll(vb1,vb2);
				dabba3.getChildren().addAll(vb3,vb4);
				dabba4.getChildren().addAll(dabba2,dabba3);
				
				gp2.add(dabba4, 0, 4);
				
				Label LDiag_val=new Label(Diagnosis); // Diagnosis value
				LDiag_val.setFont(new Font(20));
				dabba5.getChildren().addAll(LDiag,LDiag_val);
				//gp2.add(dabba5, 0, 5);
				Label LTR_val=new Label(TestResult); //Test Result value
				LTR_val.setFont(new Font(20));
				dabba6.getChildren().addAll(LTR,LTR_val);
				//gp2.add(dabba6, 0, 6);
				dabba7.getChildren().addAll(dabba5,dabba6);
				gp2.add(dabba7, 0,5);
				
			}
			rs1.close();
			c1.close();
		}catch(Exception se) {
			se.printStackTrace();
		}

		Scene myScene1 = new Scene(gp2);
		RRep.setMaximized(true);
		RRep.setScene(myScene1);
		RRep.show();
	}	
	//PRESCRIPTION PART
	//Table view and data
		private TableView Ptable;
		private ObservableList<ObservableList> dataP;
		public void Pres_buildData(String s,String EMAIL)
		{
			dataP=FXCollections.observableArrayList();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
	          	Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
				//SQL for selecting ReportId,Date,DoctorConsulted from Report table
				String SQL="SELECT PresId,DocN,PresDate FROM prescription"+" WHERE Depart='"+s+"'AND eid='"+EMAIL+"'"+"GROUP BY PresId";
				ResultSet rs=c.createStatement().executeQuery(SQL);
				
				/*TO add table column dynamically*/
				  
		            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
		                //We are using non property style for making dynamic table
		                final int j = i;
		                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
		                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
		                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
		                        return new SimpleStringProperty(param.getValue().get(j).toString());
		                    }
		                });
		               
		                Ptable.getColumns().addAll(col);
		                 }//end of for loop
				
				//Adding data to observable list
				
				while(rs.next()) {
					 ObservableList<String> row = FXCollections.observableArrayList();
		                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
		                    //Iterate Column
		                    row.add(rs.getString(i));
		                }
		               dataP.add(row);

				}
				//adding to table view
				
				Ptable.setItems(dataP);
			}catch(Exception e ){
				e.printStackTrace();
				System.out.println("Error on building data");
			}
		}
	void Prescription(String emid) {
		Stage Pro = new Stage();
		Pro.setTitle("Prescription");
		GridPane rM= new GridPane();
		rM.setHgap(10);
		rM.setVgap(10);
		rM.setMinSize(700, 700);
		rM.setPadding(new Insets(10,10,10,10)); //margins around the whole grid
		rM.setAlignment(Pos.CENTER);
		Image image3 = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg"); 
        BackgroundImage backgroundimage3 = new BackgroundImage(image3,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundPosition.CENTER,  
                                             BackgroundSize.DEFAULT); 
        Background background = new Background(backgroundimage3); 
		rM.setBackground(background);

		//creating label- REPORTS
		Label TP1=new Label("PRESCRIPTIONS ");
		TP1.setFont(new Font("Copperplate Gothic Light",42));
		rM.add(TP1, 0, 0);
		//Creating Separator
		Separator S=new Separator();
		S.setPrefSize(250, 20);
		rM.add(S,0,1);
		//Creating Hbox to group the label,listview and 
		HBox Phbox=new HBox();
		Phbox.setSpacing(50);
				
		Label TP2=new Label("Select the department of your choice\nPlease select only one department.");
		TP2.setFont(new Font("Copperplate Gothic Light",20));
		rM.add(TP2, 0, 2);
		
		ObservableList<String> PDepartmentTypes=FXCollections.observableArrayList("ENT Specialist","Pediatrician","Gynocologist","Neurologist","General Physician","Orthopedic");
		ListView<String> Pdept=new ListView<String>(PDepartmentTypes);
		Pdept.setPrefSize(200,100);
		Pdept.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
						
		Button Button_SubmitP = new Button("Submit");
        Button_SubmitP.setFont(new Font("Century Gothic",18));
        Button_SubmitP.setPrefSize(100,45);
        Button_SubmitP.setStyle("-fx-base: #7ecece;");
        Button_SubmitP.setTooltip(new Tooltip("Click to view the list of Prescriptions"));
        
        Phbox.getChildren().addAll(TP2,Pdept,Button_SubmitP);
        Phbox.setAlignment(Pos.CENTER);
        rM.add(Phbox, 0, 2);
        
        final Label lp1=new Label("List of your prescriptions");
		lp1.setFont(new Font("Century Gothic",20));
      //creating a table
		 Ptable=new TableView();
        //Action when button is clicked
        Button_SubmitP.setOnAction(e->{
        	ObservableList<String> D =Pdept.getSelectionModel().getSelectedItems();
        	String list="";
        	for(String m:D) {
        		list+=m+"";
        	}
        	String selection= list;
        	Pres_buildData(selection,emid);
        	
        }
        );
        Ptable.setMaxWidth(275);
		final VBox vpbox = new VBox();
        vpbox.setSpacing(5);
        vpbox.setPadding(new Insets(10, 0, 0, 10));
        vpbox.getChildren().addAll(lp1, Ptable);
        vpbox.setAlignment(Pos.CENTER);
        rM.add(vpbox,0,3);
              
		
		Label TP3=new Label("Enter the prescription id to view the Prescription details");
		TP3.setFont(new Font("Copperplate Gothic Light",20));
		
		TextField Preptxt=new TextField();
		
		Button PrepSubmit= new Button("Submit");
		PrepSubmit.setFont(new Font("Century Gothic",18));
		PrepSubmit.setPrefSize(100,40);
		PrepSubmit.setStyle("-fx-base: #7ecece;");
		PrepSubmit.setTooltip(new Tooltip("Click to view Prescription"));
        
		final HBox vpbox2 = new HBox();
        vpbox2.setSpacing(5);
        vpbox2.setPadding(new Insets(10, 10, 10, 10));
        vpbox2.getChildren().addAll(TP3,Preptxt,PrepSubmit);
        
        rM.add(vpbox2,0,4);

     	Alert PrepAl = new Alert(AlertType.NONE);
		PrepSubmit.setOnAction(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent ae) {
				if(Preptxt.getText().isEmpty()) {//first name field empty
					PrepAl.setAlertType(AlertType.ERROR);
					PrepAl.setTitle("Field Blank");
					PrepAl.setHeaderText("ERROR");
					PrepAl.setContentText("This field cannot be empty");
					PrepAl.showAndWait();
				}
				else {
					//printing data in another scene 
					showPrescription(Preptxt.getText(),emid);
					Preptxt.clear();
					
					
				}
			}
		});
		
		Pro.setScene(new Scene(rM));
		Pro.setMaximized(true);
		Pro.show();
	}
	public void showPrescription(String cond,String EMAIL) {
		Stage PrepR=new Stage();
		PrepR.setTitle("Prescription Details");
		GridPane gp3=new GridPane();
		gp3.setVgap(10);
		gp3.setHgap(10);
		gp3.setMinSize(800,750);
		gp3.setPadding(new Insets(10,10,10,10));
		gp3.setAlignment(Pos.CENTER);
		Image image = new Image("https://img.freepik.com/free-vector/abstract-medical-wallpaper-template-design_53876-61802.jpg?size=626&ext=jpg"); 
        BackgroundImage backgroundimage1 = new BackgroundImage(image,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundRepeat.REPEAT,  
                                          BackgroundPosition.CENTER,  
                                             BackgroundSize.DEFAULT); 
        Background background1 = new Background(backgroundimage1); 
		gp3.setBackground(background1);
		//ADDING THE IMAGE AS THE TITLE
		String url1="https://cdn.iconscout.com/icon/premium/png-256-thumb/hospital-symbol-2-769855.png";
		Image hc1 = new Image (url1,80,0,true,true);
		ImageView hcn1 = new ImageView(hc1);
		Label pd1 = new Label ("ABS Hospital",hcn1);
		pd1.setFont(new Font("Copperplate Gothic Light",60));
		gp3.add(pd1,0,0);
		
		//Separator
		Separator sep1 = new Separator();
		sep1.setPrefSize(250,20);
		gp3.add(sep1, 0, 1);
		
		//Separator
		Separator sep2 = new Separator();
		sep2.setPrefSize(250,20);
		sep2.setOrientation(Orientation.VERTICAL);
		
		//Report label
		Label HeadP=new Label("PRESCRIPTION");
		HeadP.setFont(new Font("Copperplate Gothic Light",40));
		HeadP.setAlignment(Pos.CENTER);
		gp3.add(HeadP, 0, 2);	
		
		//creating the headings
		Label Lpei = new Label("Patient Email Id: "); // Patient Email Id
		Lpei.setFont(new Font("Segoe UI Semibold",18));
		
		Label  LpRI= new Label("Report Id: ");// Patient's Report ID
		LpRI.setFont(new Font("Segoe UI Semibold",20));
		
		Label LpPName = new Label("Patient Name: "); //Patient's Name
		LpPName.setFont(new Font("Segoe UI Semibold",18));

		Label LpDName=new Label("Doctor: "); //Doctor's Name
		LpDName.setFont(new Font("Segoe UI Semibold",18));

		Label LpRD=new Label("Date: "); //DATE
		LpRD.setFont(new Font("Segoe UI Semibold",18));

		Label LpDiag=new Label("Medicine: "); //Medicine
		LpDiag.setFont(new Font("Segoe UI Semibold",20));

		Label LpTR=new Label("Dosage: "); //Dosage
		LpTR.setFont(new Font("Segoe UI Semibold",20));
		
		Label LpDep=new Label("Department: "); //Department Consulted
		LpDep.setFont(new Font("Segoe UI Semibold",20));
		
		HBox Pdabba=new HBox();
		Pdabba.setSpacing(5);
		Pdabba.setPadding(new Insets(10, 10, 10, 10));
		
		VBox Pvb1=new VBox();
		Pvb1.setSpacing(10);
		Pvb1.setPadding(new Insets(10,10,10,10));
		Pvb1.getChildren().addAll(LpRD,LpDName,LpDep);
		
		VBox Pvb2=new VBox();
		Pvb2.setSpacing(10);
		Pvb2.setPadding(new Insets(10,10,10,10));
		
		VBox Pvb3=new VBox();
		Pvb3.setSpacing(10);
		Pvb3.setPadding(new Insets(10,10,10,10));
		Pvb3.getChildren().addAll(Lpei ,LpPName);
		
		VBox Pvb4=new VBox();
		Pvb4.setSpacing(10);
		Pvb4.setPadding(new Insets(10,10,10,10));
		
		HBox Pdabba2=new HBox();
		Pdabba2.setSpacing(10);
		Pdabba2.setPadding(new Insets(10, 10, 10, 10));
		
		HBox Pdabba3=new HBox();
		Pdabba3.setSpacing(10);
		Pdabba3.setPadding(new Insets(10, 10, 10, 10));
		
		HBox Pdabba4=new HBox();
		Pdabba4.setSpacing(20);
		Pdabba4.setPadding(new Insets(10, 10, 10, 10));
		
		VBox Pdabba5=new VBox();
		Pdabba5.setSpacing(20);
		Pdabba5.setPadding(new Insets(10, 10, 10, 10));
		
		VBox Pdabba6=new VBox();
		Pdabba6.setSpacing(20);
		Pdabba6.setPadding(new Insets(10, 10, 10, 10));
		
		HBox Pdabba7=new HBox();
		Pdabba7.setSpacing(20);
		Pdabba7.setPadding(new Insets(10, 10, 10, 10));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
          	Connection c2=DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS_Hospital",sql_UN,sql_PW);
			String SQL3="SELECT *from prescription WHERE PresId='"+cond+"'AND eid='"+EMAIL+"'";// AND eid='"+Email+"'; add this while merging
			ResultSet rs2=c2.createStatement().executeQuery(SQL3);
			while(rs2.next())
			{
				String eid=rs2.getString("eid");
				String PresId=rs2.getString("PresId");
				String PFN=rs2.getString("PFN");
				String PLN=rs2.getString("PLN");
				String DocN=rs2.getString("DocN");
				String PresDate=rs2.getString("PresDate");
				String Medicine=rs2.getString("Medicine");
				String Dosage=rs2.getString("Dosage");
				String Depart=rs2.getString("Depart");
				
				Label LRI_valp=new Label(PresId); //ReportId value
				Lpei.setFont(new Font(20));
				Lpei.setContentDisplay(ContentDisplay.CENTER);
				Pdabba.getChildren().addAll(LpRI,LRI_valp);
				gp3.add(Pdabba, 0, 3);
				
				Label Lei_valp = new Label(eid);  //Patient Email Id value
				Lei_valp.setFont(new Font(20));
				Label LPName_val = new Label(PFN+" "+PLN);// Patient Name value
				LPName_val.setFont(new Font(20));
				Pvb4.getChildren().addAll(Lei_valp ,LPName_val);
				
				Label LDName_valp=new Label(DocN); // Doctor Name value
				LDName_valp.setFont(new Font(20));
				Label LRD_valp=new Label(PresDate); //Prescription date value
				LRD_valp.setFont(new Font(20));
				Label LDep_valp=new Label(Depart); //Department Consulted
				LDep_valp.setFont(new Font(20));
				Pvb2.getChildren().addAll(LRD_valp,LDName_valp,LDep_valp);
				
				Pdabba2.getChildren().addAll(Pvb1,Pvb2);
				Pdabba3.getChildren().addAll(Pvb3,Pvb4);
				Pdabba4.getChildren().addAll(Pdabba2,Pdabba3);
				
				gp3.add(Pdabba4, 0, 4);
				
				Label LpDiag_val=new Label(Medicine); // Medicine value
				LpDiag_val.setFont(new Font(20));
				Pdabba5.getChildren().addAll(LpDiag,LpDiag_val);
				//gp3.add(Pdabba5, 0, 5);
				Label LpTR_val=new Label(Dosage); //Dosage value
				LpTR_val.setFont(new Font(20));
				Pdabba6.getChildren().addAll(LpTR,LpTR_val);
				Pdabba7.getChildren().addAll(Pdabba5,sep2,Pdabba6);
				gp3.add(Pdabba7, 0, 5);
							}
			rs2.close();
			c2.close();
		}catch(Exception se) {
			se.printStackTrace();
		}

		Scene myScene1 = new Scene(gp3);
		PrepR.setMaximized(true);
		PrepR.setScene(myScene1);
		PrepR.show();
	}
}