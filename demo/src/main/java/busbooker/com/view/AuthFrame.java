package busbooker.com.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

import busbooker.com.dao.UserDAO;
import busbooker.com.model.User;

public class AuthFrame extends JFrame {

    public AuthFrame() {
        setTitle("BusBooker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== LEFT: brand + hero + stats =====
        JPanel left = new GradientPanel();
        left.setPreferredSize(new Dimension(560, getHeight()));
        left.setLayout(new BorderLayout(0, 18));
        left.setBorder(new EmptyBorder(24, 32, 24, 32));

        // Brand
        JPanel brand = new JPanel(new BorderLayout());
        brand.setOpaque(false);
        JLabel logoTitle = new JLabel("🚌  BusBooker");
        logoTitle.setForeground(Color.WHITE);
        logoTitle.setFont(logoTitle.getFont().deriveFont(Font.BOLD, 36f));
        JLabel tagline = new JLabel("Your Journey, Our Priority");
        tagline.setForeground(new Color(255,255,255,220));
        tagline.setFont(tagline.getFont().deriveFont(16f));
        brand.add(logoTitle, BorderLayout.NORTH);
        brand.add(tagline, BorderLayout.SOUTH);
        left.add(brand, BorderLayout.NORTH);

        // Hero card
        JPanel heroCard = roundedPanel(24);
        heroCard.setBackground(new Color(255,255,255,40));
        heroCard.setLayout(new BorderLayout());
        JLabel heroImg = new JLabel(loadImage("/assets/img/bus_hero.png")); // taruh gambar di resources
        heroImg.setHorizontalAlignment(SwingConstants.CENTER);
        heroCard.add(heroImg, BorderLayout.CENTER);

        // Stats
        JPanel stats = new JPanel(new GridLayout(1,3,16,0));
        stats.setOpaque(false);
        stats.add(statBox("500+", "Daily Routes"));
        stats.add(statBox("50K+", "Happy Customers"));
        stats.add(statBox("24/7", "Customer Support"));

        JPanel centerWrap = new JPanel(new BorderLayout(0,18));
        centerWrap.setOpaque(false);
        centerWrap.add(heroCard, BorderLayout.CENTER);
        centerWrap.add(stats, BorderLayout.SOUTH);
        left.add(centerWrap, BorderLayout.CENTER);

        // ===== RIGHT: card tabs =====
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(248,248,248));

        JPanel card = roundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20,20,20,20));
        card.setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Login", buildLoginPanel());
        tabs.addTab("Register", buildRegisterPanel());
        card.add(tabs, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 1; gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        right.add(card, gc);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.CENTER);
    }

    /* ------------ LOGIN PANEL ------------- */
    private JPanel buildLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        JLabel title = new JLabel("Welcome Back");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton btn = new JButton("Login");
        btn.setPreferredSize(new Dimension(140,40));

        int r=0;
        gc.gridx=0; gc.gridy=r++; p.add(title, gc);
        gc.gridy=r++; p.add(labeled("Email", email), gc);
        gc.gridy=r++; p.add(labeled("Password", pass), gc);
        gc.gridy=r++; p.add(btn, gc);

        btn.addActionListener(e -> {
            try {
                String em = email.getText().trim();
                String pw = new String(pass.getPassword());
                User u = UserDAO.login(em, pw);
                if (u != null) {
                    JOptionPane.showMessageDialog(this, "Login sukses. Halo " + u.getUsername());
                    new MainMenu(u).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Email/password salah");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return p;
    }

    /* ------------ REGISTER PANEL ----------- */
    private JPanel buildRegisterPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        JLabel title = new JLabel("Create Account");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        JLabel subtitle = new JLabel("Register to start booking tickets");

        JTextField fullname = new JTextField(); // kalau mau, simpan di User.username
        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();
        JPasswordField pass2 = new JPasswordField();
        JButton btn = new JButton("Register");
        btn.setPreferredSize(new Dimension(160,40));

        int r=0;
        gc.gridx=0; gc.gridy=r++; p.add(title, gc);
        gc.gridy=r++; p.add(subtitle, gc);
        gc.gridy=r++; p.add(labeled("Full Name", fullname), gc);
        gc.gridy=r++; p.add(labeled("Email", email), gc);
        gc.gridy=r++; p.add(labeled("Password", pass), gc);
        gc.gridy=r++; p.add(labeled("Confirm Password", pass2), gc);
        gc.gridy=r++; p.add(btn, gc);

        btn.addActionListener(e -> {
            try {
                String pw1 = new String(pass.getPassword());
                String pw2 = new String(pass2.getPassword());
                if (!pw1.equals(pw2)) {
                    JOptionPane.showMessageDialog(this, "Password tidak sama"); return;
                }
                User u = new User();
                u.setUsername(fullname.getText().trim());
                u.setEmail(email.getText().trim());
                u.setPassword(pw1);
                u.setRole("user");
                boolean ok = UserDAO.register(u);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Registrasi sukses. Silakan login.");
                    // pindah ke tab Login
                    SwingUtilities.getWindowAncestor(p); // noop, hanya memastikan parent ada
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal registrasi");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        return p;
    }

    /* -------- helper UI ------- */
    private static JPanel labeled(String label, JComponent field) {
        JPanel wrap = new JPanel(new BorderLayout(0,6));
        wrap.setOpaque(false);
        JLabel l = new JLabel(label);
        field.setPreferredSize(new Dimension(340, 40));
        wrap.add(l, BorderLayout.NORTH);
        wrap.add(field, BorderLayout.CENTER);
        return wrap;
    }

    private static JPanel roundedPanel(int radius) {
        return new JPanel() {
            { setOpaque(false); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getBackground()!=null) {
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0,0,getWidth(),getHeight(), radius, radius);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
    }

    private static JLabel statBox(String value, String label) {
        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        JLabel v = new JLabel(value);
        v.setForeground(Color.WHITE);
        v.setFont(v.getFont().deriveFont(Font.BOLD, 24f));
        JLabel l = new JLabel(label);
        l.setForeground(new Color(255,255,255,220));
        box.add(v); box.add(l);

        JLabel container = new JLabel();
        container.setLayout(new BorderLayout());
        container.add(box, BorderLayout.CENTER);
        return container;
    }

    private static ImageIcon loadImage(String path) {
        try {
            URL url = AuthFrame.class.getResource(path);
            return url != null ? new ImageIcon(url) : new ImageIcon(new byte[]{});
        } catch (Exception e) { return new ImageIcon(new byte[]{}); }
    }

    // panel gradien biru → ungu
    static class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp = new GradientPaint(0,0,new Color(0x2D77FF), getWidth(), getHeight(), new Color(0x8A3DFF));
            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());
            g2.dispose();
        }
    }
}
