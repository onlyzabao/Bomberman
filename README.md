# Bomberman
## Thành viên:
- Nguyễn Trần Gia Bảo: https://github.com/onlyzabao
- Phùng Huy Hoàng: https://github.com/Hoang-Phung
## Mô tả chung
Tái hiện lại tựa game kinh điển Bomberman của NES cùng với một số chức năng tùy biến khác.
### Map
- ![](res/sprites/grass.png) **Grass** là đối tượng mà **Bomber** và **Enemy** có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](res/sprites/wall.png) **Wall** là đối tượng cố định, không thể phá hủy bằng **Bomb** cũng như không thể đặt **Bomb** lên được, **Bomber** và **Enemy** không thể di chuyển qua đối tượng này.
- ![](res/sprites/brick.png) **Brick** là đối tượng được đặt lên các ô Grass, không cho phép đặt **Bomb** lên nhưng có thể bị phá hủy bởi **Bomb** được đặt gần đó. **Bomber** và **Enemy** thông thường không thể di chuyển vào vị trí **Brick** khi nó chưa bị phá hủy.
- ![](res/sprites/portal.png) **Portal** là đối tượng được giấu phía sau một đối tượng **Brick**. Khi **Brick** đó bị phá hủy, **Portal** sẽ hiện ra và nếu tất cả **Enemy** đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của **Portal**.
### Player
- ![](res/sprites/player_down.png) **Bomber** là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
- ![](res/sprites/bomb.png) **Bomb** là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô *Grass*. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng *Flame* được tạo ra và tiêu diệt **Enemy** (thậm chí cả **Bomber**) nếu nằm trong phạm vi nổ.
- ![](res/sprites/bomb_exploded.png) **Flame** trung tâm tại vị trí **Bomb** nổ và bốn **Flame** tại bốn vị trí ô đơn vị xung quanh vị trí của **Bomb** xuất hiện theo bốn hướng ![](res/sprites/explosion_vertical.png) trên/![](res/sprites/explosion_vertical.png) dưới/![](res/sprites/explosion_horizontal.png) trái/![](res/sprites/explosion_horizontal.png) phải. Độ dài bốn **Flame** xung quanh mặc định là 1 đơn vị.
### Enemy
**Enemy** là các đối tượng mà **Bomber** phải tiêu diệt hết để có thể qua level. Mỗi **Enemy** sở hữu một đặc tính di chuyển và khả năng riêng. **Enemey** càng khó để tiêu diệt thì có điểm thưởng càng cao.
- ![](res/sprites/balloom_left1.png) **Balloon** di chuyển tịnh tiến có thể dự đoán và đổi hướng khi có va chạm, **Balloon** sẽ di chuyển hỗn loạn hơn nếu **Bomber** đến gần nó. Điểm thưởng 100.
- ![](res/sprites/oneal_left1.png) **Oneal** di chuyển ngẫu nhiên, nhanh hơn **Balloon**, đổi hướng khi có va chạm và theo đuổi **Bomber** khi ở gần. Điểm thưởng 200.
- ![](res/sprites/doll_left1.png) **Doll** di chuyển ngẫu nhiên, chậm hơn **Balloon**, đổi hướng khi va chạm và có thể ăn **Bomb** trong trạng thái chưa nổ. Điểm thưởng 300.
- ![](res/sprites/minvo_left1.png) **Minvo** di chuyển tịnh tiến giống **Balloon**, đổi hướng khi có va chạm và sẽ đổi them **Bomber** nếu nó muốn. Điểm thưởng 400.
- ![](res/sprites/kondoria_left1.png) **Kondoria** di chuyển chậm nhất nhưng có thể đi xuyên qua **Bomb** và **Brick**. Đuổi theo **Bomber** nếu **Bomber** di chuyển đến gần **Portal**. Điểm thưởng 500.
- ![](res/sprites/ovape_left.png) **Ovape** tương tự **Kondoria** nhưng nhanh và thông minh hơn. Điểm thưởng 600.
### Item
Các **Item** cũng được giấu phía sau **Brick** và chỉ hiện ra khi **Brick** bị phá hủy. **Bomber** có thể sử dụng **Item** bằng cách di chuyển vào vị trí của **Item**.
- ![](res/sprites/powerup_speed.png) **SpeedItem** tăng tốc độ di chuyển cho **Bomber**. Sinh ra **Doll** nếu bị phá hủy.
- ![](res/sprites/powerup_flames.png) **FlameItem** tăng phạm vi ảnh hưởng của **Bomb** khi nổ (độ dài các **Flame** lớn hơn). Sinh ra **Oneal** nếu bị phá hủy.
- ![](res/sprites/powerup_bombs.png) **BombItem** tăng số lượng **Bomb** có thể đặt thêm. Sinh ra **Balloon** nếu bị phá hủy.
- ![](res/sprites/powerup_bombpass.png) **BombPassItem** giúp **Bomber** có thể đi xuyên qua **Bomb**. Sinh ra **Minvo** nếu bị phá hủy.
- ![](res/sprites/powerup_wallpass.png) **WallPassItem** giúp **Bomber** có thể đi xuyên qua **Brick** (nhưng không thể đi xuyên qua **Wall**). Sinh ra **Kondoria** nếu bị phá hủy.
- ![](res/sprites/powerup_detonator.png) **DetonatorItem** giúp **Bomber** có thể kích hoạt nổ **Bomb** từ xa, tại bất kì thời điểm mà mong muốn. Sinh ra **Ovape** nếu bị phá hủy.
## Mô tả game play, xử lý va chạm và xử lý bom nổ
- Trong một màn chơi, Bomber sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệt tất cả Enemy và tìm ra vị trí Portal để có thể qua màn mới
- Bomber sẽ bị giết khi va chạm với Enemy hoặc thuộc phạm vi Bomb nổ. Lúc đấy trò chơi kết thúc.
- Enemy bị tiêu diệt khi thuộc phạm vi Bomb nổ
- Một đối tượng thuộc phạm vi Bomb nổ có nghĩa là đối tượng đó va chạm với một trong các tia lửa được tạo ra tại thời điểm một đối tượng Bomb nổ.

- Khi Bomb nổ, một Flame trung tâm![](res/sprites/bomb_exploded.png) tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb xuất hiện theo bốn hướng trên![](res/sprites/explosion_vertical.png)/dưới![](res/sprites/explosion_vertical.png)/trái![](res/sprites/explosion_horizontal.png)/phải![](res/sprites/explosion_horizontal.png). Độ dài bốn Flame xung quanh mặc định là 1 đơn vị, được tăng lên khi Bomber sử dụng các FlameItem.
- Khi các Flame xuất hiện, nếu có một đối tượng thuộc loại Brick/Wall/Grass/Portal nằm trên vị trí một trong các Flame thì độ dài Flame đó sẽ được giảm đi để sao cho Flame chỉ xuất hiện đến vị trí đối tượng Brick/Wall theo hướng xuất hiện. Lúc đó chỉ có đối tượng Brick/Wall bị ảnh hưởng bởi Flame, các đối tượng tiếp theo không bị ảnh hưởng. Còn nếu vật cản Flame là một đối tượng Bomb khác thì đối tượng Bomb đó cũng sẽ nổ ngay lập tức.


## Các công nghệ sử dụng
- Ngôn ngữ: java (phiên bản jdk 17)
- Framework: một framework dựa trên nền javaFX hỗ trợ làm game
