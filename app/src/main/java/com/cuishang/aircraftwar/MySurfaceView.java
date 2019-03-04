package com.cuishang.aircraftwar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.telecom.Call;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Vector;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Paint paint;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private GameMenu gameMenu;
    public static int screenW;
    public static int screenH;
    private boolean flag;
    private Thread th;
    private GameBg backGround;
    public static final int GAME_MENU = 0;//游戏菜单
    public static final int GAMEING = 1;//游戏中
    public static final int GAME_WIN = 2;//游戏胜利
    public static final int GAME_LOST = 3;//游戏失败
    public static final int GAME_PAUSE = -1;//游戏菜单
    //当前游戏状态(默认初始在游戏菜单界面)
    public static int gameState = GAME_MENU;
    private Bitmap bmpBackGround;
    private Bitmap bmpBoom;
    private Bitmap bmpBoosBoom;
    private Bitmap bmpButton;
    private Bitmap bmpButtonPress;
    private Bitmap bmpEnemyDuck;
    private Bitmap bmpEnemyFly;
    private Bitmap bmpEnemyBoos;
    private Bitmap bmpGameWin;
    private Bitmap bmpGameLost;
    private Bitmap bmpPlayer;
    private Bitmap bmpPlayerHp;
    private Bitmap bmpMenu;
    public static Bitmap bmpBullet;
    public static Bitmap bmpEnemyBullet;
    public static Bitmap bmpBossBullet;
    private Resources res = this.getResources();
    private Vector<Enemy> vcEnemy;
    public static Vector<Bullet> vcBulletBoss;
    private Player player;
    private boolean isBoss;
    private int count;
    private int createEnemyTime = 50;
    private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 }, { 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 2, 1 },
            { 1, 3 }, { 2, 1 }, { -1 } };
    //µ±Ç°È¡³öÒ»Î¬Êý×éµÄÏÂ±ê
    private int enemyArrayIndex;
    //ÊÇ·ñ³öÏÖBoss±êÊ¶Î»
    //Ëæ»ú¿â£¬Îª´´½¨µÄµÐ»ú¸³ÓèËæ¼´×ø±ê
    private Random random;
    //µÐ»ú×Óµ¯ÈÝÆ÷
    private Vector<Bullet> vcBullet;
    //Ìí¼Ó×Óµ¯µÄ¼ÆÊýÆ÷
    private int countEnemyBullet;
    //Ö÷½Ç×Óµ¯ÈÝÆ÷
    private Vector<Bullet> vcBulletPlayer;
    //Ìí¼Ó×Óµ¯µÄ¼ÆÊýÆ÷
    private int countPlayerBullet;
    //±¬Õ¨Ð§¹ûÈÝÆ÷
    private Vector<Boom> vcBoom;
    //ÉùÃ÷Boss
    private Boss boss;

    public MySurfaceView(Context context) {
        super(context);
        System.out.println("cuishang1 debug:PlaneView()");
        sfh = this.getHolder();
        sfh.addCallback(this);//注册回掉方法
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    public void myDraw(){

        try {
            System.out.println("cuishang1 myDraw paole");
            canvas = sfh.lockCanvas();
            System.out.println("cuishang1 canvas = " + canvas);
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);

                switch (gameState) {
                    case GAME_MENU:
                        System.out.println("cuishang1 GAME_MENU   jinlaileaaaaa");
                        gameMenu.draw(canvas, paint);
                        break;
                    case GAMEING:

                        backGround.draw(canvas, paint);

                        player.draw(canvas, paint);
                        if (isBoss == false) {

                            for (int i = 0; i < vcEnemy.size(); i++) {
                                vcEnemy.elementAt(i).draw(canvas, paint);
                            }

                            for (int i = 0; i < vcBullet.size(); i++) {
                                vcBullet.elementAt(i).draw(canvas, paint);
                            }
                        } else {

                            boss.draw(canvas, paint);

                            for (int i = 0; i < vcBulletBoss.size(); i++) {
                                vcBulletBoss.elementAt(i).draw(canvas, paint);
                            }
                        }

                        for (int i = 0; i < vcBulletPlayer.size(); i++) {
                            vcBulletPlayer.elementAt(i).draw(canvas, paint);
                        }

                        for (int i = 0; i < vcBoom.size(); i++) {
                            vcBoom.elementAt(i).draw(canvas, paint);
                        }
                        break;
                    case GAME_PAUSE:
                        break;
                    case GAME_WIN:
                        canvas.drawBitmap(bmpGameWin, 0, 0, paint);
                        break;
                    case GAME_LOST:
                        canvas.drawBitmap(bmpGameLost, 0, 0, paint);
                        break;
                }
            }
        }catch (Exception e){

        }finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    private void logic() {
        //Âß¼­´¦Àí¸ù¾ÝÓÎÏ·×´Ì¬²»Í¬½øÐÐ²»Í¬´¦Àí
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                //±³¾°Âß¼­
                backGround.logic();
                //Ö÷½ÇÂß¼­
                player.logic();
                //µÐ»úÂß¼­
                if (isBoss == false) {
                    //µÐ»úÂß¼­
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        Enemy en = vcEnemy.elementAt(i);
                        //ÒòÎªÈÝÆ÷²»¶ÏÌí¼ÓµÐ»ú £¬ÄÇÃ´¶ÔµÐ»úisDeadÅÐ¶¨£¬
                        //Èç¹ûÒÑËÀÍöÄÇÃ´¾Í´ÓÈÝÆ÷ÖÐÉ¾³ý,¶ÔÈÝÆ÷Æðµ½ÁËÓÅ»¯×÷ÓÃ£»
                        if (en.isDead) {
                            vcEnemy.removeElementAt(i);
                        } else {
                            en.logic();
                        }
                    }
                    //Éú³ÉµÐ»ú
                    count++;
                    if (count % createEnemyTime == 0) {
                        for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
                            //²ÔÓ¬
                            if (enemyArray[enemyArrayIndex][i] == 1) {
                                int x = random.nextInt(screenW - 100) + 50;
                                vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
                                //Ñ¼×Ó×ó
                            } else if (enemyArray[enemyArrayIndex][i] == 2) {
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));
                                //Ñ¼×ÓÓÒ
                            } else if (enemyArray[enemyArrayIndex][i] == 3) {
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW + 50, y));
                            }
                        }
                        //ÕâÀïÅÐ¶ÏÏÂÒ»×éÊÇ·ñÎª×îºóÒ»×é(Boss)
                        if (enemyArrayIndex == enemyArray.length - 1) {
                            isBoss = true;
                        } else {
                            enemyArrayIndex++;
                        }
                    }
                    //´¦ÀíµÐ»úÓëÖ÷½ÇµÄÅö×²
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        if (player.isCollsionWith(vcEnemy.elementAt(i))) {
                            //·¢ÉúÅö×²£¬Ö÷½ÇÑªÁ¿-1
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            //µ±Ö÷½ÇÑªÁ¿Ð¡ÓÚ0£¬ÅÐ¶¨ÓÎÏ·Ê§°Ü
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }
                    //Ã¿2ÃëÌí¼ÓÒ»¸öµÐ»ú×Óµ¯
                    countEnemyBullet++;
                    if (countEnemyBullet % 40 == 0) {
                        for (int i = 0; i < vcEnemy.size(); i++) {
                            Enemy en = vcEnemy.elementAt(i);
                            //²»Í¬ÀàÐÍµÐ»ú²»Í¬µÄ×Óµ¯ÔËÐÐ¹ì¼£
                            int bulletType = 0;
                            switch (en.type) {
                                //²ÔÓ¬
                                case Enemy.TYPE_FLY:
                                    bulletType = Bullet.BULLET_FLY;
                                    break;
                                //Ñ¼×Ó
                                case Enemy.TYPE_DUCKL:
                                case Enemy.TYPE_DUCKR:
                                    bulletType = Bullet.BULLET_DUCK;
                                    break;
                            }
                            vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
                        }
                    }
                    //´¦ÀíµÐ»ú×Óµ¯Âß¼­
                    for (int i = 0; i < vcBullet.size(); i++) {
                        Bullet b = vcBullet.elementAt(i);
                        if (b.isDead) {
                            vcBullet.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }
                    //´¦ÀíµÐ»ú×Óµ¯ÓëÖ÷½ÇÅö×²
                    for (int i = 0; i < vcBullet.size(); i++) {
                        if (player.isCollsionWith(vcBullet.elementAt(i))) {
                            //·¢ÉúÅö×²£¬Ö÷½ÇÑªÁ¿-1
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            //µ±Ö÷½ÇÑªÁ¿Ð¡ÓÚ0£¬ÅÐ¶¨ÓÎÏ·Ê§°Ü
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }
                    //´¦ÀíÖ÷½Ç×Óµ¯ÓëµÐ»úÅö×²
                    for (int i = 0; i < vcBulletPlayer.size(); i++) {
                        //È¡³öÖ÷½Ç×Óµ¯ÈÝÆ÷µÄÃ¿¸öÔªËØ
                        Bullet blPlayer = vcBulletPlayer.elementAt(i);
                        for (int j = 0; j < vcEnemy.size(); j++) {
                            //Ìí¼Ó±¬Õ¨Ð§¹û
                            //È¡³öµÐ»úÈÝÆ÷µÄÃ¿¸öÔªÓëÖ÷½Ç×Óµ¯±éÀúÅÐ¶Ï
                            if (vcEnemy.elementAt(j).isCollsionWith(blPlayer)) {
                                vcBoom.add(new Boom(bmpBoom, vcEnemy.elementAt(j).x, vcEnemy.elementAt(j).y, 7));
                            }
                        }
                    }
                } else {//BossÏà¹ØÂß¼­
                    //Ã¿0.5ÃëÌí¼ÓÒ»¸öÖ÷½Ç×Óµ¯
                    boss.logic();
                    if (countPlayerBullet % 10 == 0) {
                        //BossµÄÃ»·¢·èÖ®Ç°µÄÆÕÍ¨×Óµ¯
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 35, boss.y + 40, Bullet.BULLET_FLY));
                    }
                    //Boss×Óµ¯Âß¼­
                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        Bullet b = vcBulletBoss.elementAt(i);
                        if (b.isDead) {
                            vcBulletBoss.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }
                    //Boss×Óµ¯ÓëÖ÷½ÇµÄÅö×²
                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
                            //·¢ÉúÅö×²£¬Ö÷½ÇÑªÁ¿-1
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            //µ±Ö÷½ÇÑªÁ¿Ð¡ÓÚ0£¬ÅÐ¶¨ÓÎÏ·Ê§°Ü
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }
                    //Boss±»Ö÷½Ç×Óµ¯»÷ÖÐ£¬²úÉú±¬Õ¨Ð§¹û
                    for (int i = 0; i < vcBulletPlayer.size(); i++) {
                        Bullet b = vcBulletPlayer.elementAt(i);
                        if (boss.isCollsionWith(b)) {
                            if (boss.hp <= 0) {
                                //ÓÎÏ·Ê¤Àû
                                gameState = GAME_WIN;
                            } else {
                                //¼°Ê±É¾³ý±¾´ÎÅö×²µÄ×Óµ¯£¬·ÀÖ¹ÖØ¸´ÅÐ¶¨´Ë×Óµ¯ÓëBossÅö×²¡¢
                                b.isDead = true;
                                //BossÑªÁ¿¼õ1
                                boss.setHp(boss.hp - 1);
                                //ÔÚBossÉÏÌí¼ÓÈý¸öBoss±¬Õ¨Ð§¹û
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25, boss.y + 30, 5));
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35, boss.y + 40, 5));
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 45, boss.y + 50, 5));
                            }
                        }
                    }
                }
                //Ã¿1ÃëÌí¼ÓÒ»¸öÖ÷½Ç×Óµ¯
                countPlayerBullet++;
                if (countPlayerBullet % 20 == 0) {
                    vcBulletPlayer.add(new Bullet(bmpBullet, player.x + 15, player.y - 20, Bullet.BULLET_PLAYER));
                }
                //´¦ÀíÖ÷½Ç×Óµ¯Âß¼­
                for (int i = 0; i < vcBulletPlayer.size(); i++) {
                    Bullet b = vcBulletPlayer.elementAt(i);
                    if (b.isDead) {
                        vcBulletPlayer.removeElement(b);
                    } else {
                        b.logic();
                    }
                }
                //±¬Õ¨Ð§¹ûÂß¼­
                for (int i = 0; i < vcBoom.size(); i++) {
                    Boom boom = vcBoom.elementAt(i);
                    if (boom.playEnd) {
                        //²¥·ÅÍê±ÏµÄ´ÓÈÝÆ÷ÖÐÉ¾³ý
                        vcBoom.removeElementAt(i);
                    } else {
                        vcBoom.elementAt(i).logic();
                    }
                }
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;

        }
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            //System.out.println("cuishang1 xianchengpaole");
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame();
        flag = true;
        System.out.println("cuishang1 surfaceCreated");
        th = new Thread(this);
        th.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (gameState) {
            case GAME_MENU:

                gameMenu.onTouchEvent(event);
                break;
            case GAMEING:
                player.GamingOnTouch(event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:

                break;
        }
        return true;
    }
    private void initGame() {
        if (gameState == GAME_MENU) {
            bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.background);
            bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
            bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
            bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
            bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
            bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck);
            bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
            bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.enemy_pig);
            bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
            bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelost);
            bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
            bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
            bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
            bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
            bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
            bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);

            vcBoom = new Vector<Boom>();

            vcBullet = new Vector<Bullet>();

            vcBulletPlayer = new Vector<Bullet>();

            gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress);

            backGround = new GameBg(bmpBackGround);

            player = new Player(bmpPlayer, bmpPlayerHp);

            vcEnemy = new Vector<Enemy>();

            random = new Random();

            boss = new Boss(bmpEnemyBoos);

            vcBulletBoss = new Vector<Bullet>();
        }
    }
}
