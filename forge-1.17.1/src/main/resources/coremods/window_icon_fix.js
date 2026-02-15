// Coremod: Skip glfwSetWindowIcon on macOS to prevent GLFW_FEATURE_UNAVAILABLE crash.
//
// macOS does not support window icons for regular windows. LWJGL 3.3.0+ (GLFW 3.3.4+)
// reports GLFW_FEATURE_UNAVAILABLE (65548) when glfwSetWindowIcon is called on macOS.
// Minecraft 1.17.1's Window class treats this as a fatal error and crashes.
// This coremod patches the method containing glfwSetWindowIcon to return early on macOS.

function initializeCoreMod() {
    var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
    var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
    var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
    var LdcInsnNode = Java.type('org.objectweb.asm.tree.LdcInsnNode');
    var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
    var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
    var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
    var AbstractInsnNode = Java.type('org.objectweb.asm.tree.AbstractInsnNode');

    return {
        'skip_window_icon_on_macos': {
            'target': {
                'type': 'CLASS',
                'name': 'com.mojang.blaze3d.platform.Window'
            },
            'transformer': function(classNode) {
                var methods = classNode.methods;

                for (var i = 0; i < methods.size(); i++) {
                    var method = methods.get(i);
                    var instructions = method.instructions;
                    var iter = instructions.iterator();

                    while (iter.hasNext()) {
                        var insn = iter.next();
                        if (insn.getType() == AbstractInsnNode.METHOD_INSN
                            && insn.getOpcode() == Opcodes.INVOKESTATIC
                            && insn.owner.equals('org/lwjgl/glfw/GLFW')
                            && insn.name.equals('glfwSetWindowIcon')) {

                            ASMAPI.log('INFO', '[FancyIceCream] Patching Window.' + method.name + ' to skip glfwSetWindowIcon on macOS');

                            // Insert at method start:
                            //   if (System.getProperty("os.name", "").toLowerCase().contains("mac")) return;
                            var patch = new InsnList();
                            var continueLabel = new LabelNode();

                            patch.add(new LdcInsnNode('os.name'));
                            patch.add(new LdcInsnNode(''));
                            patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                'java/lang/System', 'getProperty',
                                '(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;', false));
                            patch.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                'java/lang/String', 'toLowerCase',
                                '()Ljava/lang/String;', false));
                            patch.add(new LdcInsnNode('mac'));
                            patch.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                'java/lang/String', 'contains',
                                '(Ljava/lang/CharSequence;)Z', false));
                            patch.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
                            patch.add(new InsnNode(Opcodes.RETURN));
                            patch.add(continueLabel);

                            instructions.insert(patch);
                            return classNode;
                        }
                    }
                }

                ASMAPI.log('WARN', '[FancyIceCream] Could not find glfwSetWindowIcon in Window class');
                return classNode;
            }
        }
    };
}
