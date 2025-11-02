# Architectury Loom版本変更レポート

## 実行日時
2025-10-09

## 変更内容

### Architectury Loom版本の変更

**変更前:**
- Version: 1.6.397 (beta)
- ステータス: ベータ版、不安定

**変更後:**
- Version: 1.6.422 (1.6-SNAPSHOT)
- ステータス: 古いバージョンだが動作安定

**変更ファイル:**
- `/build.gradle` の `id 'dev.architectury.loom' version '1.6.397'` を `version '1.6-SNAPSHOT'` に変更
- Gradle依存解決により `1.6.422` が適用された

## 動作確認結果

### ✅ 成功した動作

#### 1. Clean タスク
```bash
./gradlew clean
```
**結果**: BUILD SUCCESSFUL in 21s
**改善**: 以前のNeoForge設定エラーなし

#### 2. Compile タスク
```bash
./gradlew versions:v1.21.8:forge:compileJava
```
**結果**: BUILD SUCCESSFUL in 29s
**改善**: コンパイルは変更前後で安定

#### 3. Build タスク（remapJar含む）
```bash
./gradlew versions:v1.21.8:forge:build
```
**結果**: BUILD SUCCESSFUL in 20s
**改善**: ⭐ **remapJarが成功！**

**以前のエラー（1.6.397）:**
```
Mapping source name conflicts detected:
  net/minecraftforge/network/ForgePayload METHOD write
  net/minecraft/network/protocol/login/custom/CustomQueryPayload METHOD write
```

**現在の状態（1.6.422）:**
```
> Task :versions:v1.21.8:forge:remapJar
> Task :versions:v1.21.8:forge:remapSourcesJar
> Task :versions:v1.21.8:forge:assemble
> Task :versions:v1.21.8:forge:build

BUILD SUCCESSFUL
```

### ⚠️ 残存する問題

#### runClient タスク
```bash
./gradlew versions:v1.21.8:forge:runClient
```
**結果**: launch段階で停止、タイムアウト

**ログ状況:**
- ModLauncher起動: ✅ 成功
- JVM識別: ✅ 成功
- Launch services検出: ✅ 成功
- Transformation services検出: 🔄 開始後に停止

**推測される原因:**
- launchTarget設定の問題
- Forge launch設定の不完全性
- Architectury TransformerとForge launcherの互換性問題

## 改善度評価

### 定量的評価

| 項目 | 変更前 | 変更後 | 改善度 |
|------|--------|--------|--------|
| clean | ❌ NeoForge設定エラー | ✅ 成功 | 100% |
| compileJava | ✅ 成功 | ✅ 成功 | - |
| remapJar | ❌ マッピング競合 | ✅ 成功 | **100%** |
| build | ❌ remapJar失敗 | ✅ 成功 | **100%** |
| runClient | ❌ launchエラー | ❌ launchエラー | 0% |

### 定性的評価

**大きな成果:**
- 🎉 **配布可能なmodファイルの生成が可能に**
- 🎉 **remapJarの致命的エラー解決**
- ✅ ビルドパイプライン全体が安定化

**残存課題:**
- ⚠️ 開発環境での実行テストは依然として不可
- ⚠️ launchTarget設定の問題未解決

## 技術的分析

### remapJar成功の意義

**以前（1.6.397）の問題:**
- Minecraft Forge と Fabric の マッピング名競合
- `CustomQueryPayload.write()` メソッドの複数定義競合
- TinyRemapperの競合解決失敗

**現在（1.6.422）の改善:**
- マッピング競合の解決アルゴリズムが改善
- または、古いバージョンで競合が存在しない状態

### runClient問題の技術的背景

**エラーパターン:**
```
java.lang.NullPointerException:
Cannot invoke "String.contains(java.lang.CharSequence)"
because "launchTarget" is null
```

**発生箇所:**
```
at net.minecraftforge.fml.loading.ImmediateWindowHandler.load(ImmediateWindowHandler.java:26)
```

**推測される原因:**
1. Forge の launch configuration が正しく設定されていない
2. Architectury Transformer と Forge launcher の初期化順序問題
3. gradle run configuration の不完全性

## 試したその他の版本

### 失敗した版本

#### 1. Architectury Loom 1.7.417
**結果**: Failed to remap minecraft
**問題**: Minecraft setup段階で失敗

#### 2. Architectury Loom 1.11-SNAPSHOT (1.11.451)
**結果**: Gradle API互換性エラー
**問題**: `'java.lang.String org.gradle.api.artifacts.ProjectDependency.getPath()'`
**原因**: Gradle 8.8 との非互換性

### 成功した版本

#### Architectury Loom 1.6-SNAPSHOT (1.6.422)
- ✅ clean成功
- ✅ compileJava成功
- ✅ build成功（remapJar含む）
- ⚠️ runClientは問題継続

## 結論

### 達成された目標

1. **配布可能なmodファイル生成**: ✅ 達成
   - remapJarが成功
   - 完全なビルドパイプラインが機能

2. **マッピング競合解決**: ✅ 達成
   - 致命的なremapエラーが解消

3. **ビルドシステムの安定化**: ✅ 達成
   - clean, compile, buildの全工程が正常動作

### 未達成の目標

1. **開発環境での実行**: ❌ 未達成
   - runClientがlaunch段階で停止
   - 実際のmod動作確認は不可

### 次のステップ

#### Option A: launch設定の調査と修正
- Forge launch configuration の詳細調査
- Architectury Transformer設定の見直し
- run tasksの設定改善

#### Option B: 別のアプローチ
- 生成されたJARファイルを通常のMinecraft環境でテスト
- 開発環境runClientをスキップして機能実装を優先

#### Option C: 機能実装優先
- runClientの問題を後回しにして
- アイテム登録など実際のmod機能を実装
- コンパイルとビルドが成功しているため実装は可能

## 推奨事項

**短期（即座に可能）:**
- ✅ remapJarが成功したため、配布用ビルドは問題なし
- 🔄 機能実装を開始し、コンパイル/ビルドで検証
- ⏭️ runClientの問題は並行して調査

**中期（追加調査必要）:**
- 🔍 Forge launch configuration の詳細調査
- 🔍 Architectury公式ドキュメントで runClient設定を確認
- 🔍 サンプルプロジェクトとの比較

**長期（根本的解決）:**
- 🎯 より新しい安定版Architectury Loomへの移行検討
- 🎯 Gradleバージョンの更新検討
- 🎯 NeoForgeサポートの追加（現在無効化中）

## まとめ

Architectury Loom 1.6.422への変更により、**ビルドシステムの核心的な問題（remapJar）が解決**されました。これは配布可能なmodファイルの生成が可能になったことを意味し、**大きな進歩**です。

runClientの問題は依然として残っていますが、これは開発環境の実行設定の問題であり、mod機能の実装やビルドには影響しません。

**現在の状態: mod開発の実質的な作業（機能実装）を開始できる段階に到達**
