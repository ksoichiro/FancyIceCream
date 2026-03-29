# Gradle 9 + FG7 Migration Status

## 背景・動機
Fabric 26.1 対応に Fabric Loom 1.14+ が必要 → Loom 1.14+ は Gradle API 9.2.0 を要求 →
Gradle 9 にすると ForgeGradle 6.x が動作しない → ForgeGradle 7.x (mavenLocal `7.0.17`) へ移行が必要。
FG7 は API が FG6 と大きく異なるため全 Forge build.gradle をリライト。
JustCoordinates (`~/src/github.com/ksoichiro/JustCoordinates`) が同じ移行済みで参考にしている。

## テンプレート構成
| テンプレート | プラグイン | 対象バージョン |
|---|---|---|
| B | `net.neoforged.moddev.legacyforge` | 1.17.1 - 1.20.1 (7バージョン) |
| C | `net.minecraftforge.gradle` (FG7) | 1.16.5, 1.20.2 - 1.21.11 (14バージョン) |

**変更履歴**: 1.16.5 は Arch Loom から FG7 に変更（MCP マッピング互換性のため）。1.20.2/1.20.4/1.20.6 は legacyforge から FG7 に変更（legacyforge が Forge 48-50 非対応のため）。

## FG7 API メモ (ソース確認済み: 7.0.17)
- Run configs: `systemProperty(key, value)`, `args(...)`, `getWorkingDir()` (DirectoryProperty)
- Repos: `minecraft.mavenizer(it)`, `maven fg.forgeMaven`, `maven fg.minecraftLibsMaven`
- Deps: `implementation minecraft.dependency("group:artifact:version")`
- AT: `setAccessTransformer(true)` on dependency, or `accessTransformers.from(file)` on extension
- Mods: `mods { "modid" { source sourceSets.main } }` (runs の configureEach 内)
- Run register: `register('client') { ... }`

## 検証コマンド
```bash
./gradlew clean build -Ptarget_mc_version=1.21.9 -x test   # Template C (FG7)
./gradlew clean build -Ptarget_mc_version=1.20.1 -x test   # Template B (legacyforge)
./gradlew clean build -Ptarget_mc_version=1.16.5 -x test   # Template A (arch loom)
./gradlew clean build -Ptarget_mc_version=26.1              # Fabric + NeoForge
```

## 完了した変更

### インフラストラクチャ
- [x] `gradle/wrapper/gradle-wrapper.properties`: gradle-8.14.2-all.zip → gradle-9.4.0-bin.zip
- [x] `gradle.properties`: FG7, fabric_loom_26, architectury_loom 追加、parchment 削除、FG6 systemProp 削除
- [x] `settings.gradle`: mavenLocal追加、architectury.dev追加、target_mc_version別プラグイン出し分け、foojay 1.0.0
- [x] `build.gradle` (root): legacyforge プラグイン追加、ParchmentMC削除、java toolchain条件分岐
- [x] `props/26.1.properties`: enabled_platforms に fabric 追加

### Forge build.gradle テンプレート
- [x] Template A (1.16.5): `dev.architectury.loom` プラグイン
- [x] Template B (1.17.1-1.20.6): `net.neoforged.moddev.legacyforge` プラグイン (10ファイル)
- [x] Template C (1.21.1-1.21.11): `net.minecraftforge.gradle` (FG7) プラグイン (10ファイル)
  - `property` → `systemProperty` に修正済み
  - `programArguments` → `args` に修正済み
  - `mods {}` を `configureEach` 内に配置済み
  - common/shared 重複チェック追加済み（forge ディレクトリも対象に含める）
  - forge/common ソース重複時に `source configurations.commonJava` をスキップ

### mods.toml 修正
- [x] `${file.jarVersion}` → `${version}` に変更 (全バージョン)
- [x] コメント内の `${}` 参照を削除 (Groovy テンプレート展開エラー防止)

### AT (Access Transformer) 修正
- [x] 全 forge AT ファイルを SRG 名 → official 名に変更
- [x] 全 FG7 build.gradle に `accessTransformers.from(atFile)` を minecraft 拡張内に追加
- [x] forge/1.21.1 に AT ファイル新規作成 (以前は存在しなかった)

### Fabric
- [x] `fabric/26.1/build.gradle`: net.fabricmc.fabric-loom、mappings/accessWidener削除、implementation化

## 解決済み: FG7 Access Transformer が compilation に反映されない

### 原因
AT ファイルが SRG 名 (`f_382642_`) を使用していたが、FG7 の mavenizer は official 名前空間で動作するため、official 名 (`STATIC_DEFINITIONS`) を使う必要があった。

FG6 では AT は SRG→official のリマッピングパイプライン内で処理されたため SRG 名が有効だったが、FG7 では mavenizer が `--access-transformer` 引数で AT ファイルを受け取り、official 名前空間の jar に直接適用する。SRG 名はマッチしないためサイレントに無視されていた。

### 修正内容
1. 全 forge AT ファイル (`forge/*/src/main/resources/META-INF/accesstransformer.cfg`) を official 名に変更
2. 全 FG7 build.gradle に `accessTransformers.from(atFile)` を minecraft 拡張内に追加
3. `setAccessTransformer(true)` は dependency 側にも残置（互換性のため）

### 検証済み
- 1.21.9: compileJava 成功
- 1.21.8: compileJava 成功

## ビルド検証状況 (2026-03-30)

1.17.1〜26.1 の 21 バージョン: `clean build -x test -x runData` 成功。
1.16.5: Gradle 9 対象外 (Gradle 8 + FG6 で個別ビルド)。

## runClient 動作検証状況 (2026-03-30)

### 正常動作
| バージョン | プラットフォーム | 備考 |
|---|---|---|
| 1.17.1 | Forge | 進捗・レシピ含め正常 |
| 1.18 | Forge | 進捗・レシピ含め正常 |
| 1.18.2 | Forge | `--add-opens` 追加で解決 |
| 1.19.4 | Forge | |
| 1.20.1 | Forge, Fabric | |
| 1.20.2 | Fabric | |
| 1.20.4 | Forge, Fabric | `sourceSets.each` マージ + `-XstartOnFirstThread` で解決 |
| 1.20.6 | Forge, Fabric | `-XstartOnFirstThread` で解決 |
| 1.21.11 | Forge, Fabric, NeoForge | `-XstartOnFirstThread` で解決 |
| 26.1 | Fabric, NeoForge | |

### 動作するが問題あり
| バージョン | プラットフォーム | 問題 |
|---|---|---|
| 1.19 | Forge | 進捗・レシピなし (移行前からの可能性あり) |
| 1.19.3 | Forge | 進捗・レシピなし (移行前からの可能性あり) |

### 未検証
| バージョン | プラットフォーム |
|---|---|
| 1.21.1〜1.21.10 | Forge, Fabric |
| 1.20.2 | Forge |

## 未解決の課題

### 1.16.5: Gradle 9 ビルド対象外
FG7 が reobfJar をサポートしないため、production jar のメソッド名がリマップされない。
Gradle 8 + FG6 で個別ビルドが必要。`buildAll` からは除外済み。
```bash
# Gradle 8 環境で実行
./gradlew clean build -Ptarget_mc_version=1.16.5 -x test
```

### Forge 26.1
ForgeGradle 6.0.51 NPE (upstream バグ) のためビルド不可。上流修正待ち。

## 解決済みの課題

### macOS Forge runClient (`-XstartOnFirstThread`)
FG7 が macOS で `-XstartOnFirstThread` を自動設定しない。全 FG7 build.gradle の client run config に macOS 判定付きで追加。

### legacyforge `--add-opens` (1.18.2, 1.19, 1.19.3)
SecureJarHandler が `java.lang.invoke` への `--add-opens` を必要とする。
`jvmArguments.addAll '--add-opens', 'java.base/java.lang.invoke=cpw.mods.securejarhandler'` を legacyforge run config に追加。

### Forge 1.20.4 `sourceSets.each` マージ
クラスとリソースが別ディレクトリだったため mod ローダーがクラスを検出できなかった。`sourceSets.each` マージ + `copyGeneratedResources` パターンを追加。

### Fabric IceCreamCupRenderer 修正
Fabric 1.20.1〜1.20.6 で `getModelResourceLoc()` override が欠落していたため、カップ設置時にスタンドのベースモデルが描画されていた。4バージョンで修正済み・動作確認済み。

### 26.1 Fabric/NeoForge 対応
- `fabric/26.1/build.gradle`: `loom { accessWidenerPath }` ブロック追加
- `fancyicecream.accesswidener`: namespace `named` → `official` (26.1 は unobfuscated)
- `FancyIceCreamFabricTab.java`: `FabricItemGroup.builder()` → `CreativeModeTab.builder(Row.TOP, 0)`
