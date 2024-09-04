﻿## Minecraftで価値観を共有

価値観共有ボードゲーム「ito」をMinecraftで遊べるプラグインです。  
与えられたテーマを基に話し合い、それぞれの数字を小さい順に宣言します

全員が宣言した後、数字を開示して正しく並んでいるかを判定します。

**コマンド一覧**  
`/ito gm` ゲームマスターになります(ゲームマスターのみitoを開始できます)  
`/ito join` itoに参加します  
`/ito leave` itoから退室します  
`/ito console` コンソールへ出力するかどうかを変更します(/ito gmによるゲームマスターのみ実行可能)  

**ゲームの進め方**  
ゲームマスターには`棒`が与えられます。  
`棒`で左クリックすることでメニューが開きます。  
`ダイアモンドの剣`でゲームを開始してください。  
全員が宣言を終えたら`松明`で成功判定を行ってください。  
![screenshot of menu](https://github.com/user-attachments/assets/6f3d3e06-9980-452a-96ea-39360c976321)


**メニュー一覧**  
以下はメニューのアイテム一覧を示します
1. `ダイヤモンドの剣`  
   ゲームを開始します
2. `紙`  
   テーマを変更します、ゲーム進行中テーマを変更したいときに利用してください
3. `松明`  
   数字を開示して成功判定を行います
4. `雪玉`  
   提出されているテーマ一覧と個数を表示します
5. `パン`  
   宣言します

**テーマの提出**  
アカシアの看板に記入することでテーマの提出ができます。  
看板は行ごとに記入できる文字数に制限があるため適宜改行してください。  
改行されても連結された文字列がテーマとして送信されます。
>[!NOTE]  
>サーバ内のすべてのアカシアの看板がテーマ提出用アイテムになるので注意してください

**configファイルについて**  
このプラグインを実行させるとpluginsフォルダにItoフォルダが作成されconfig.ymlというファイルが作成され、このファイルにはゲームマスターの情報やゲームで採用されたテーマなどが保存されます。テーマに困ったときに参考にしてください。
