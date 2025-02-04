/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.clases;

/**
 *
 * @author SHIRLEY
 */
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class DragDropImage extends JFrame {

    private JLabel imageLabel;

    public DragDropImage() {
        setTitle("Arrastrar y Soltar Imagen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        imageLabel = new JLabel("Arrastra una imagen aquí", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.LIGHT_GRAY);
        imageLabel.setPreferredSize(new Dimension(100, 100));

        // Habilitar Drag and Drop
        new DropTarget(imageLabel, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    Object transferData = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    if (transferData instanceof java.util.List) {
                        java.util.List<File> fileList = (java.util.List<File>) transferData;
                        if (!fileList.isEmpty()) {
                            File file = fileList.get(0);
                            if (isImageFile(file)) {
                                displayImage(file);
                            } else {
                                JOptionPane.showMessageDialog(null, "Solo se permiten imágenes.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    event.dropComplete(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(imageLabel, BorderLayout.CENTER);
    }

    // Método para verificar si el archivo es una imagen
    private boolean isImageFile(File file) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif"};
        String fileName = file.getName().toLowerCase();
        for (String ext : validExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // Método para mostrar la imagen en el JLabel
    private void displayImage(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setText(""); // Quitar el texto
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método Main para ejecutar el programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DragDropImage().setVisible(true));
    }
}
