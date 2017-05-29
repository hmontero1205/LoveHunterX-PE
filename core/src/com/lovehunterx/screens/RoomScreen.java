package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.screens.ui.Arrow;
import com.lovehunterx.screens.ui.FixedGroup;

import java.util.ArrayList;

public class RoomScreen extends LHXScreen {
    public Stage stage;
    private String room;
    private ArrayList<Furniture> inventory;
    private SideBar invBar;

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
        Image back = new Image(new Texture(Gdx.files.internal("roomBack.jpg")));
        back.setPosition(centerX(back), centerY(back));
        stage.addActor(back);



        LoveHunterX.getConnection().registerListener("join", new Listener() {
            @Override
            public void handle(Packet packet) {
                System.out.println(packet.toJSON());
                if (packet.getData("user").equals(LoveHunterX.getUsername())) {
                    room = packet.getData("room");
                }

                Player player = new Player(packet.getData("user"));
                player.setX(Float.valueOf(packet.getData("x")));
                player.setY(Float.valueOf(packet.getData("y")));
                stage.addActor(player);
            }
        });

        Packet join = Packet.createJoinRoomPacket(LoveHunterX.getUsername());
        LoveHunterX.getConnection().send(join);

        Arrow right = new Arrow(Arrow.RIGHT);
        right.setPosition(stage.getWidth() - right.getWidth() - 10, 20);
        Arrow left = new Arrow(Arrow.LEFT);
        left.setPosition(stage.getWidth() - right.getWidth() - left.getWidth() - 20, 20);

        FixedGroup fixed = new FixedGroup();
        fixed.addActor(left);
        fixed.addActor(right);
        stage.addActor(fixed);

        invBar = new SideBar(0,65,65,350);
        stage.addActor(invBar);
        inventory = new ArrayList<Furniture>();
        inventory.add(new Furniture(200,30, "Love Sofa"));
        inventory.add(new Furniture(200,30, "Love Sofa"));
        inventory.add(new Furniture(200,30, "Love Sofa"));
        inventory.add(new Furniture(200,30, "Love Sofa"));
        inventory.add(new Furniture(200,30, "Tea Table"));
        for(Furniture f : inventory) {
            invBar.addItem(f);
        }



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        LoveHunterX.getConnection().clearListeners();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    private float centerX(Actor a) {
        return (stage.getWidth() / 2) - (a.getWidth() / 2);
    }

    private float centerY(Actor a) {
        return (stage.getHeight() / 2) - (a.getHeight() / 2);
    }

    public class SideBar extends Group {
        private float x;
        private float y;
        private float w;
        private float h;
        private Table table;
        private TextButton tab;
        private boolean extended;
        private Table list;
        private ScrollPane container;
        private int length;

        public SideBar(float x, float y, float w, float h)  {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            table = new Table();
            table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tableBack.png")))));
            table.setSize(w,h);
            table.setPosition(-1 * w,y);
            table.top();
            table.pad(3);
            list = new Table();
            list.top();
            container = new ScrollPane(list);
            container.setSize(w,h);
            container.setOverscroll(false,false);
            table.add(container);
            tab = new TextButton(">", Assets.SKIN);
            tab.setWidth(35);
            tab.setPosition(-10,table.getY() + (table.getHeight() / 2) - 10);
            tab.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    if(!extended) {
                        pullOut();
                        extended = true;
                    } else {
                        pullIn();
                        extended = false;
                    }
                }
            });
            addActor(table);
            addActor(tab);
        }

        private void pullOut() {
            MoveByAction act = Actions.moveBy(table.getWidth() - 3, 0, .25F, Interpolation.smoother);
            tab.addAction(act);
            MoveByAction act2 = Actions.moveBy(table.getWidth() - 3, 0, .25F, Interpolation.smoother);
            table.addAction(act2);
            tab.setText("<");
        }

        private void pullIn() {
            MoveByAction act = Actions.moveBy(-table.getWidth() + 3, 0, .25F, Interpolation.smoother);
            tab.addAction(act);
            MoveByAction act2 = Actions.moveBy(-table.getWidth() + 3, 0, .25F, Interpolation.smoother);
            table.addAction(act2);
            tab.setText(">");
        }

        public void addItem(Furniture f) {
            SideBarItem item = new SideBarItem(f);
            this.list.add(item);
            this.list.row();
            length++;
        }

        private class SideBarItem extends Table{
            private Actor bar;
            public SideBarItem(Furniture f) {
                final String itemName = f.getDesc();
                final Furniture fObj = f;
                Image item = new Image(new Texture(Gdx.files.internal(itemName + " Icon.png")));
                Label desc  = new Label(itemName, Assets.SKIN);
                desc.setFontScale(.65f);
                desc.setWidth(item.getWidth()-4);
                if(length > 0){
                    bar = new Image(new Texture(Gdx.files.internal("sep.png")));
                    add(bar);
                    row();
                }
                add(item);
                row();
                add(desc);
                final Actor itemRef = this;
                addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        //remove self and if was top item, removes the bar from its successor
                        list.removeActor(itemRef);
                        inventory.remove(fObj);
                        Gdx.app.log("inventory",inventory.toString());
                        Array<Cell> cells = list.getCells();
                        ArrayList<Cell> cellList = new ArrayList<Cell>();
                        for(Cell c : cells) {
                            if(c.getActor() != null) {
                                cellList.add(c);
                            }
                        }
                        if(cellList.size() > 0)
                            ((SideBar.SideBarItem) (cellList.get(0).getActor())).removeBar();

                        //add furniture to scrren
                        stage.addActor(fObj);
                        length--;


                    }
                });
            }

            public void removeBar() {
                if(bar != null)
                    removeActor(bar);
            }
        }


    }

    private class Furniture extends Table {
        private float x;
        private float y;
        private String desc;
        private boolean optsToggled;
        private TextButton xBut;

        public Furniture(float ix, float iy, String desc) {
            final Furniture fRef = this;
            this.x = ix;
            this.y = iy;
            this.desc = desc;
            setPosition(x,y);
            Image item = new Image(new Texture(Gdx.files.internal(desc + ".png")));
            setSize(170,150);
            xBut = new TextButton("X", Assets.SKIN);
            xBut.setWidth(35);
            xBut.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    remove();
                    inventory.add(fRef);
                    invBar.addItem(fRef);

                }
            });
            add(item);
            row();
            addListener(new DragListener(){
                public void drag(InputEvent event, float x, float y, int pointer) {
                    if(optsToggled) {
                        float deltaY = y - getHeight() / 2;
                        float deltaX = x - getWidth() / 2;
                        if (getY() + deltaY <= 60 && getY() + deltaY >= 30 && getX() + deltaX <= 470 && getX() + deltaX >= 0) {
                            moveBy(deltaX, deltaY);
                        }
                    }
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    toggleOpts();
                }
            });

            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    toggleOpts();
                }

            });

            top();
            center();
        }

        public String getDesc() {
            return this.desc;
        }

        public void toggleOpts() {
            optsToggled = !optsToggled;
            if(optsToggled) {
                addActor(xBut);
            } else {
                removeActor(xBut);
            }
        }


    }
}
