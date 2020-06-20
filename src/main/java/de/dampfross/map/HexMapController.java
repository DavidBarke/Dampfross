package de.dampfross.map;

import de.dampfross.editor.EditState;
import de.dampfross.transformation.Rotation;
import de.dampfross.transformation.Translation;
import de.dampfross.transformation.Zoom;
import de.dampfross.utilities.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HexMapController extends JPanel  {
    private final HexMap hexMap;

    private final HexMapMouseInputAdapter hexMapMouseInputAdapter;
    private final HexMapClickHandler hexMapClickHandler;
    private final HexMapMouseHandler hexMapMouseHandler;

    HexEdge hoverEdge;
    HexEntity hoverEntity;

    private final Camera camera;

    private EditState editState = EditState.MOUNTAIN;

    public HexMapController() {
        this.hexMap = new HexMap();

        this.camera = new Camera(20, 0.03);

        this.hexMapClickHandler = new HexMapClickHandler(this);
        this.hexMapMouseHandler = new HexMapMouseHandler(this);

        this.hexMapMouseInputAdapter = new HexMapMouseInputAdapter(
                this, hexMapClickHandler, hexMapMouseHandler
        );
        this.addMouseListener(hexMapMouseInputAdapter);
        this.addMouseMotionListener(hexMapMouseInputAdapter);
        this.addMouseWheelListener(new Zoom(camera));
        this.addKeyListener(new Translation(camera));
        this.addKeyListener(new Rotation(camera));

        ActionListener movePerformer = e -> {
            camera.move();
            repaint();
        };

        new Timer(40, movePerformer).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        HexMapDrawer.draw(g, camera, this);
    }

    public HexMap getHexMap() {
        return hexMap;
    }

    public EditState getEditState() {
        return editState;
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
    }

    public HexEdge getHoverEdge() {
        return hoverEdge;
    }

    public void setHoverEdge(HexEdge hoverEdge) {
        this.hoverEdge = hoverEdge;
    }

    public HexEntity getHoverEntity() {
        return hoverEntity;
    }

    public void setHoverEntity(HexEntity hoverEntity) {
        this.hoverEntity = hoverEntity;
    }

    public Camera getCamera() {
        return camera;
    }

    public void addClickedEntityListener(HexMapClickedEntityListener listener) {
        hexMapClickHandler.addClickedEntityListener(listener);
    }
}
