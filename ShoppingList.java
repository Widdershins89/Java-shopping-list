package ShoppingList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingList extends JFrame {

    private DefaultListModel<Item> sListModel;
    private JList<Item> sList;
    private JTextField newItemNameField;
    private JTextField newItemInfoField;
    private JButton addButton;
    private JButton removeButton;
    private JButton increaseButton;
    private JButton decreaseButton;

    public ShoppingList() {
        setTitle("Shopping List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sListModel = new DefaultListModel<>();
        sList = new JList<>(sListModel);
        JScrollPane scrollPane = new JScrollPane(sList);
        add(scrollPane, BorderLayout.CENTER);

        newItemNameField = new JTextField();
        newItemInfoField = new JTextField();
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        increaseButton = new JButton("+");
        decreaseButton = new JButton("-");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = newItemNameField.getText();
                String itemInfo = newItemInfoField.getText();
                if (!itemName.isEmpty()) {
                    Item newItem = new Item(itemName, itemInfo);
                    sListModel.addElement(newItem);
                    newItemNameField.setText("");
                    newItemInfoField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = sList.getSelectedIndex();
                if (selectedIndex != -1) {
                    sListModel.remove(selectedIndex);
                }
            }
        });

        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = sList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Item selectedItem = sListModel.getElementAt(selectedIndex);
                    selectedItem.incrementQuantity();
                    sList.repaint();
                }
            }
        });

        decreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = sList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Item selectedItem = sListModel.getElementAt(selectedIndex);
                    selectedItem.decrementQuantity();
                    sList.repaint();
                }
            }
        });

        JPanel newItemPanel = new JPanel(new BorderLayout());
        newItemPanel.add(new JLabel("Name:"), BorderLayout.WEST);
        newItemPanel.add(newItemNameField, BorderLayout.CENTER);
        newItemPanel.add(new JLabel("Info:"), BorderLayout.SOUTH);
        newItemPanel.add(newItemInfoField, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(newItemPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.SOUTH);

        sList.setCellRenderer(new ItemRenderer());
        sList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setVisible(true);
    }

    private class Item {
        private String name;
        private int quantity;
        private String info;

        public Item(String name, String info) {
            this.name = name;
            this.quantity = 1;
            this.info = info;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void incrementQuantity() {
            quantity++;
        }

        public void decrementQuantity() {
            if (quantity > 1) {
                quantity--;
            }
        }

        public String getInfo() {
            return info;
        }

        @Override
        public String toString() {
            return name + " (" + quantity + ") " + info;
        }
    }

    private class ItemRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText(((Item) value).toString());
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShoppingList();
            }
        });
    }
}





