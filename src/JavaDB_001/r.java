package JavaDB_001;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class r extends JFrame{
    JButton button ;
    JLabel label;
    JTextField jtf;

    public r(){
    super("retrieve image from database in java");
    
    button = new JButton("Retrieve");
    button.setBounds(250,300,100,40);
    
    jtf = new JTextField();
    jtf.setBounds(360,310,100,20);
    
    label = new JLabel();
    label.setBounds(10,10,670,250);
    
    add(button);
    add(label);
    add(jtf);
    
    button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/db_images","root","");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from AddTourPackage");
                if(rs.next()){
                    byte[] img = rs.getBytes("img");



                    //Resize The ImageIcon
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    label.setIcon(newImage);
                }
                
                else{
                    JOptionPane.showMessageDialog(null, "No Data");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        
        }
    });
    
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.decode("#bdb76b"));
    setLocationRelativeTo(null);
    setSize(700,400);
    setVisible(true);
    } 
   }