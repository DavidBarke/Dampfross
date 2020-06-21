package de.dampfross.hex.map;

import de.dampfross.editor.EditState;
import de.dampfross.hex.edge.HexEdge;
import de.dampfross.hex.entity.HexEntity;
import de.dampfross.transformation.Rotation;
import de.dampfross.transformation.Translation;
import de.dampfross.transformation.Zoom;
import de.dampfross.utilities.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HexMapController extends JPanel  {
    private HexMap hexMap;

    private final HexMapMouseInputAdapter hexMapMouseInputAdapter;
    private final HexMapClickHandler hexMapClickHandler;
    private final HexMapMouseHandler hexMapMouseHandler;
    private final HexMapDragReleasedHandler hexMapDragReleasedHandler;
    private final HexMapLoader hexMapLoader;

    HexEdge hoverEdge;
    HexEntity hoverEntity;

    private Camera camera;

    private EditState editState = EditState.MOUNTAIN;

    public HexMapController() {
        this.hexMap = new HexMap();

        this.camera = new Camera(20, 0.03);

        this.hexMapClickHandler = new HexMapClickHandler(this);
        this.hexMapMouseHandler = new HexMapMouseHandler(this);
        this.hexMapDragReleasedHandler = new HexMapDragReleasedHandler(this);
        this.hexMapMouseInputAdapter = new HexMapMouseInputAdapter(
                this, hexMapClickHandler, hexMapMouseHandler, hexMapDragReleasedHandler
        );
        this.addMouseListener(hexMapMouseInputAdapter);
        this.addMouseMotionListener(hexMapMouseInputAdapter);
        this.addMouseWheelListener(new Zoom(camera));
        this.addKeyListener(new Translation(camera));
        this.addKeyListener(new Rotation(camera));

        this.hexMapLoader = new HexMapLoader(this);

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

    public void addClickedEntityListener(HexMapClickedEntityListener listener) {
        hexMapClickHandler.addClickedEntityListener(listener);
    }

    public HexMap getHexMap() {
        return hexMap;
    }

    public void setHexMap(HexMap hexMap) {
        this.hexMap = hexMap;
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

    public HexMapLoader getHexMapLoader() {
        return hexMapLoader;
    }
}
