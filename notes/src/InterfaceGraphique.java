import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InterfaceGraphique extends JFrame {
    private List<Etudiant> etudiants = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;

    public InterfaceGraphique() {
        setTitle("Gestion des Étudiants");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création du tableau
        String[] colonnes = {"Nom", "Hören", "Lessen", "Vokabular", "Grammatik", "Schreiben", "Sprechen", "Med", "Moyenne"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        JButton ajouterButton = new JButton("Ajouter un étudiant");
        JButton afficherButton = new JButton("Afficher les étudiants");
        JButton trierButton = new JButton("Afficher par ordre de mérite");
        JButton sauvegarderButton = new JButton("Sauvegarder");
        JButton enregistrerSousButton = new JButton("Enregistrer sous");
        JButton chargerButton = new JButton("Charger"); // Bouton "Charger"
        JButton modifierButton = new JButton("Modifier un étudiant");
        JButton supprimerButton = new JButton("Supprimer un étudiant");

        // Ajout des boutons au panneau
        panelBoutons.add(ajouterButton);
        panelBoutons.add(afficherButton);
        panelBoutons.add(trierButton);
        panelBoutons.add(sauvegarderButton);
        panelBoutons.add(enregistrerSousButton);
        panelBoutons.add(chargerButton); // Ajout du bouton "Charger"
        panelBoutons.add(modifierButton);
        panelBoutons.add(supprimerButton);

        // Ajout du panneau des boutons à la fenêtre
        add(panelBoutons, BorderLayout.NORTH);

        // Actions des boutons
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEtudiant();
            }
        });

        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherEtudiants();
            }
        });

        trierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherParOrdreDeMerite();
            }
        });

        sauvegarderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sauvegarderDonnees();
            }
        });

        enregistrerSousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enregistrerSous();
            }
        });

        chargerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chargerDonnees();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierEtudiant();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerEtudiant();
            }
        });
    }

    private void ajouterEtudiant() {
        JTextField nomField = new JTextField();
        JTextField hörenField = new JTextField();
        JTextField lessenField = new JTextField();
        JTextField vokabularField = new JTextField();
        JTextField grammatikField = new JTextField();
        JTextField schreibenField = new JTextField();
        JTextField sprechenField = new JTextField();
        JTextField medField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Hören:"));
        panel.add(hörenField);
        panel.add(new JLabel("Lessen:"));
        panel.add(lessenField);
        panel.add(new JLabel("Vokabular:"));
        panel.add(vokabularField);
        panel.add(new JLabel("Grammatik:"));
        panel.add(grammatikField);
        panel.add(new JLabel("Schreiben:"));
        panel.add(schreibenField);
        panel.add(new JLabel("Sprechen:"));
        panel.add(sprechenField);
        panel.add(new JLabel("Med:"));
        panel.add(medField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ajouter un étudiant", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nom = nomField.getText();
                double hören = Double.parseDouble(hörenField.getText());
                double lessen = Double.parseDouble(lessenField.getText());
                double vokabular = Double.parseDouble(vokabularField.getText());
                double grammatik = Double.parseDouble(grammatikField.getText());
                double schreiben = Double.parseDouble(schreibenField.getText());
                double sprechen = Double.parseDouble(sprechenField.getText());
                double med = Double.parseDouble(medField.getText());

                Etudiant etudiant = new Etudiant(nom, hören, lessen, vokabular, grammatik, schreiben, sprechen, med);
                etudiants.add(etudiant);
                JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès !");
                afficherEtudiants();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des notes valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void afficherEtudiants() {
        tableModel.setRowCount(0); // Effacer les anciennes données
        for (Etudiant etudiant : etudiants) {
            Object[] row = {
                    etudiant.getNom(),
                    etudiant.getHören(),
                    etudiant.getLessen(),
                    etudiant.getVokabular(),
                    etudiant.getGrammatik(),
                    etudiant.getSchreiben(),
                    etudiant.getSprechen(),
                    etudiant.getMed(),
                    etudiant.getMoyenne()
            };
            tableModel.addRow(row);
        }
    }

    private void afficherParOrdreDeMerite() {
        List<Etudiant> etudiantsTries = new ArrayList<>(etudiants);
        Collections.sort(etudiantsTries, new Comparator<Etudiant>() {
            @Override
            public int compare(Etudiant e1, Etudiant e2) {
                return Double.compare(e2.getMoyenne(), e1.getMoyenne()); // Tri décroissant
            }
        });

        tableModel.setRowCount(0);
        for (Etudiant etudiant : etudiantsTries) {
            Object[] row = {
                    etudiant.getNom(),
                    etudiant.getHören(),
                    etudiant.getLessen(),
                    etudiant.getVokabular(),
                    etudiant.getGrammatik(),
                    etudiant.getSchreiben(),
                    etudiant.getSprechen(),
                    etudiant.getMed(),
                    etudiant.getMoyenne()
            };
            tableModel.addRow(row);
        }
    }

    private void sauvegarderDonnees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("etudiants.txt"))) {
            for (Etudiant etudiant : etudiants) {
                writer.write(etudiant.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Données sauvegardées avec succès !");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enregistrerSous() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer sous");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                for (Etudiant etudiant : etudiants) {
                    writer.write(etudiant.toString());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Données enregistrées sous " + fileToSave.getName() + " avec succès !");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void chargerDonnees() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Charger un fichier");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                etudiants.clear(); // Efface la liste actuelle des étudiants
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    etudiants.add(Etudiant.fromString(ligne)); // Convertit la ligne en objet Etudiant
                }
                JOptionPane.showMessageDialog(this, "Données chargées avec succès !");
                afficherEtudiants(); // Met à jour l'affichage
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierEtudiant() {
        String[] nomsEtudiants = etudiants.stream().map(Etudiant::getNom).toArray(String[]::new);
        String nom = (String) JOptionPane.showInputDialog(this, "Choisissez un étudiant à modifier :", "Modifier un étudiant", JOptionPane.PLAIN_MESSAGE, null, nomsEtudiants, nomsEtudiants[0]);

        if (nom != null) {
            Etudiant etudiant = etudiants.stream().filter(e -> e.getNom().equals(nom)).findFirst().orElse(null);
            if (etudiant != null) {
                JTextField hörenField = new JTextField(String.valueOf(etudiant.getHören()));
                JTextField lessenField = new JTextField(String.valueOf(etudiant.getLessen()));
                JTextField vokabularField = new JTextField(String.valueOf(etudiant.getVokabular()));
                JTextField grammatikField = new JTextField(String.valueOf(etudiant.getGrammatik()));
                JTextField schreibenField = new JTextField(String.valueOf(etudiant.getSchreiben()));
                JTextField sprechenField = new JTextField(String.valueOf(etudiant.getSprechen()));
                JTextField medField = new JTextField(String.valueOf(etudiant.getMed()));

                JPanel panel = new JPanel(new GridLayout(7, 2));
                panel.add(new JLabel("Hören:"));
                panel.add(hörenField);
                panel.add(new JLabel("Lessen:"));
                panel.add(lessenField);
                panel.add(new JLabel("Vokabular:"));
                panel.add(vokabularField);
                panel.add(new JLabel("Grammatik:"));
                panel.add(grammatikField);
                panel.add(new JLabel("Schreiben:"));
                panel.add(schreibenField);
                panel.add(new JLabel("Sprechen:"));
                panel.add(sprechenField);
                panel.add(new JLabel("Med:"));
                panel.add(medField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Modifier les notes de " + nom, JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        etudiant.setHören(Double.parseDouble(hörenField.getText()));
                        etudiant.setLessen(Double.parseDouble(lessenField.getText()));
                        etudiant.setVokabular(Double.parseDouble(vokabularField.getText()));
                        etudiant.setGrammatik(Double.parseDouble(grammatikField.getText()));
                        etudiant.setSchreiben(Double.parseDouble(schreibenField.getText()));
                        etudiant.setSprechen(Double.parseDouble(sprechenField.getText()));
                        etudiant.setMed(Double.parseDouble(medField.getText()));
                        JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès !");
                        afficherEtudiants();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des notes valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void supprimerEtudiant() {
        String[] nomsEtudiants = etudiants.stream().map(Etudiant::getNom).toArray(String[]::new);
        String nom = (String) JOptionPane.showInputDialog(this, "Choisissez un étudiant à supprimer :", "Supprimer un étudiant", JOptionPane.PLAIN_MESSAGE, null, nomsEtudiants, nomsEtudiants[0]);

        if (nom != null) {
            etudiants.removeIf(e -> e.getNom().equals(nom));
            JOptionPane.showMessageDialog(this, "Étudiant supprimé avec succès !");
            afficherEtudiants();
        }
    }
}