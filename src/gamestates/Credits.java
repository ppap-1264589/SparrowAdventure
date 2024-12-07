package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import utilz.LoadSave;

public class Credits extends State implements Statemethods {
    private BufferedImage backgroundImg, creditsImg, treasureImg;
    private int bgX, bgY, bgW, bgH;
    private float bgYFloat;
    private int offsetTwoSide = (int)(50*Game.SCALE);
  
    private ArrayList<ShowEntity> entitiesList;

    public Credits(Game game) {
        super(game);
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        creditsImg = LoadSave.GetSpriteAtlas(LoadSave.CREDITS);
        treasureImg = LoadSave.GetSpriteAtlas(LoadSave.TREASURE_BG);
        bgW = (int) (creditsImg.getWidth() * Game.SCALE);
        bgH = (int) (creditsImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2;
        bgY = Game.GAME_HEIGHT;
        loadEntities();
    }
    
    private void loadEntities() {
        entitiesList = new ArrayList<>();
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PLAYER_PIRATE), 5, 64, 40), (int) (0 - offsetTwoSide), (int) (Game.GAME_HEIGHT * 0.8)));
        entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PLAYER_CAPY), 5, 64, 40), (int) (Game.GAME_WIDTH + offsetTwoSide), (int) (Game.GAME_HEIGHT * 0.8)));
    }
    
    private BufferedImage[] getIdleAni(BufferedImage atlas, int spritesAmount, int width, int height) {
        BufferedImage[] arr = new BufferedImage[spritesAmount];
        for (int i = 0; i < spritesAmount; i++)
            arr[i] = atlas.getSubimage(width * i, 0, width, height);
        return arr;
    }

	@Override
    public void update() {
    	bgYFloat -= 0.2f;
        for (ShowEntity se : entitiesList)
            se.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(treasureImg, (int)(85 * Game.SCALE), (int)(25 * Game.SCALE), (int)(Game.GAME_WIDTH/2 * 0.90f), (int)(Game.GAME_HEIGHT * 0.9f), null);
        g.drawImage(creditsImg, (int)(bgX + Game.GAME_WIDTH/8 - 15*Game.SCALE), (int) (bgY + bgYFloat), (int)(bgW), bgH, null);
        
        entitiesList.get(0).draw(g, 1);
        entitiesList.get(1).draw(g, -1);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bgYFloat = 0;
            setGamestate(Gamestate.MENU);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
    	// TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    
//    if (left && !right) {
//        xSpeed -= walkSpeed;
//        flipX = width;
//        flipW = -1;
//    }
//    if (right && !left) {
//        xSpeed += walkSpeed;
//        flipX = 0;
//        flipW = 1;
//    }
    
    private class ShowEntity {
        private BufferedImage[] idleAnimation;
        private int x, y, aniIndex, aniTick;

        public ShowEntity(BufferedImage[] idleAnimation, int x, int y) {
            this.idleAnimation = idleAnimation;
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g, int fliped) {
            g.drawImage(idleAnimation[aniIndex], x, y, (int) (idleAnimation[aniIndex].getWidth() * 4 * fliped), (int) (idleAnimation[aniIndex].getHeight() * 4), null);
        }

        public void update() {
            aniTick++;
            if (aniTick >= 25) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= idleAnimation.length)
                    aniIndex = 0;
            }

        }
    }
}
