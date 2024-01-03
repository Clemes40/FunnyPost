package application;

import java.net.URL;
import java.util.ResourceBundle;
import entite.post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.modeleConnection;
import service.PostService;

public class VueController implements Initializable {
	
    @FXML
    private Button btnConect;
    @FXML
    private Label infoConnect;
    @FXML
    private ListView<post> listPost;
    @FXML
    private Label lblBody;
    @FXML
    private Button initialisation;
    @FXML
    private Button deletePost;
    @FXML
    private TextField searchID;

    private PostService postService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            modeleConnection con = new modeleConnection();
            listPost.getItems().addAll(con.con.getPosts());
            listPost.setOnMouseClicked(this::handleMouseClick);

            postService = new PostService(con.con.conn);

            initialisation.setOnAction(event -> handleInit(event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleMouseClick(MouseEvent event) {
        post selectedPost = listPost.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            lblBody.setText(selectedPost.getBody());
            lblBody.setWrapText(true);
        }
    }
    
    private void handleInit(ActionEvent event) {
        postService.GetAndSavePost();
        refreshPostList();
    }

    private void refreshPostList() {
        try {
            modeleConnection con = new modeleConnection();
            listPost.getItems().clear();
            listPost.getItems().addAll(con.con.getPosts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleSearch(ActionEvent event) {
    	String id = searchID.getText();
    	post postcherche = postService.getPostById(id);
    	if (postcherche != null) {
    		lblBody.setText(postcherche.getBody());
    		lblBody.setWrapText(true);
            for (int i = 0; i < listPost.getItems().size(); i++) {
                if (listPost.getItems().get(i).getId().equals(postcherche.getId())) {
                    listPost.getSelectionModel().select(i);
                    listPost.getFocusModel().focus(i);
                    listPost.scrollTo(i);
                    break;
                }
            }
    	} else {
    		lblBody.setText("Aucun post trouve");
    	}
    }
    
    @FXML
    private void handleDelete(ActionEvent event) {
        post selectedPost = listPost.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            postService.deletePostById(selectedPost.getId().toString());
            refreshPostList();
            lblBody.setText("Le post a bien été supprimé");
        } else {
        	lblBody.setText("Veuillez selectionner un post afin de le supprimer");
        }
    }
}
