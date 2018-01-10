/*   1:    */ package com.asinfo.as2.util;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.Caja;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.seguridad.modelo.RolAnonimo;
/*  12:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  13:    */ import java.security.MessageDigest;
/*  14:    */ import java.security.NoSuchAlgorithmException;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.faces.context.ExternalContext;
/*  19:    */ import javax.faces.context.FacesContext;
/*  20:    */ import javax.servlet.http.HttpServletRequest;
/*  21:    */ import javax.servlet.http.HttpSession;
/*  22:    */ 
/*  23:    */ public class AppUtil
/*  24:    */ {
/*  25: 34 */   private static final char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*  26:    */   private static final String SALT1 = "6X 7g<Gs% l3f-y*pq,$K6@SC/gI &R7[EZ($FSf@%,hVU )_9,[2quO]7YVy6 (-]3";
/*  27:    */   private static final String SALT2 = "M-Lt 7*E|on|= tX.2j-$cE]/Sk*SK?o)x [4d!u  lgAPutg l@FH5-l7sUChh9R";
/*  28: 39 */   private static char[] map1 = new char[64];
/*  29:    */   private static byte[] map2;
/*  30:    */   
/*  31:    */   static
/*  32:    */   {
/*  33: 41 */     int i = 0;
/*  34: 42 */     for (char c = 'A'; c <= 'Z'; c = (char)(c + '\001')) {
/*  35: 42 */       map1[(i++)] = c;
/*  36:    */     }
/*  37: 43 */     for (char c = 'a'; c <= 'z'; c = (char)(c + '\001')) {
/*  38: 43 */       map1[(i++)] = c;
/*  39:    */     }
/*  40: 44 */     for (char c = '0'; c <= '9'; c = (char)(c + '\001')) {
/*  41: 44 */       map1[(i++)] = c;
/*  42:    */     }
/*  43: 45 */     map1[(i++)] = '+';map1[(i++)] = '/';
/*  44:    */     
/*  45:    */ 
/*  46: 48 */     map2 = new byte[''];
/*  47: 50 */     for (int i = 0; i < map2.length; i++) {
/*  48: 50 */       map2[i] = -1;
/*  49:    */     }
/*  50: 51 */     for (int i = 0; i < 64; i++) {
/*  51: 51 */       map2[map1[i]] = ((byte)i);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static char[] encodeBase64(byte[] in)
/*  56:    */   {
/*  57: 63 */     return encodeBase64(in, in.length);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static char[] encodeBase64(byte[] in, int iLen)
/*  61:    */   {
/*  62: 73 */     int oDataLen = (iLen * 4 + 2) / 3;
/*  63: 74 */     int oLen = (iLen + 2) / 3 * 4;
/*  64: 75 */     char[] out = new char[oLen];
/*  65: 76 */     int ip = 0;
/*  66: 77 */     for (int op = 0; ip < iLen; op++)
/*  67:    */     {
/*  68: 79 */       int i0 = in[(ip++)] & 0xFF;
/*  69: 80 */       int i1 = ip < iLen ? in[(ip++)] & 0xFF : 0;
/*  70: 81 */       int i2 = ip < iLen ? in[(ip++)] & 0xFF : 0;
/*  71: 82 */       int o0 = i0 >>> 2;
/*  72: 83 */       int o1 = (i0 & 0x3) << 4 | i1 >>> 4;
/*  73: 84 */       int o2 = (i1 & 0xF) << 2 | i2 >>> 6;
/*  74: 85 */       int o3 = i2 & 0x3F;
/*  75: 86 */       out[(op++)] = map1[o0];
/*  76: 87 */       out[(op++)] = map1[o1];
/*  77: 88 */       out[op] = (op < oDataLen ? map1[o2] : '=');op++;
/*  78: 89 */       out[op] = (op < oDataLen ? map1[o3] : '=');
/*  79:    */     }
/*  80: 90 */     return out;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static RolAnonimo getAnonymousRole()
/*  84:    */   {
/*  85: 93 */     return new RolAnonimo();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void invalidateSession()
/*  89:    */   {
/*  90: 97 */     FacesContext fc = FacesContext.getCurrentInstance();
/*  91: 98 */     if (fc != null)
/*  92:    */     {
/*  93: 99 */       HttpServletRequest request = (HttpServletRequest)fc.getExternalContext().getRequest();
/*  94:    */       
/*  95:101 */       request.getSession().invalidate();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static String encriptaEnMD5(String stringAEncriptar)
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:110 */       stringAEncriptar = "6X 7g<Gs% l3f-y*pq,$K6@SC/gI &R7[EZ($FSf@%,hVU )_9,[2quO]7YVy6 (-]3" + stringAEncriptar + "M-Lt 7*E|on|= tX.2j-$cE]/Sk*SK?o)x [4d!u  lgAPutg l@FH5-l7sUChh9R";
/* 104:111 */       MessageDigest msgd = MessageDigest.getInstance("MD5");
/* 105:112 */       byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
/* 106:113 */       StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
/* 107:114 */       for (int i = 0; i < bytes.length; i++)
/* 108:    */       {
/* 109:116 */         int bajo = bytes[i] & 0xF;
/* 110:117 */         int alto = (bytes[i] & 0xF0) >> 4;
/* 111:118 */         strbCadenaMD5.append(CONSTS_HEX[alto]);
/* 112:119 */         strbCadenaMD5.append(CONSTS_HEX[bajo]);
/* 113:    */       }
/* 114:121 */       return strbCadenaMD5.toString();
/* 115:    */     }
/* 116:    */     catch (NoSuchAlgorithmException e) {}
/* 117:123 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void setAtributo(String atributo, Object object)
/* 121:    */   {
/* 122:135 */     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(atributo, object);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static Object getAtributo(String atributo)
/* 126:    */   {
/* 127:146 */     if (FacesContext.getCurrentInstance() != null) {
/* 128:147 */       return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(atributo);
/* 129:    */     }
/* 130:149 */     return null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static void removeAtributo(String atributo)
/* 134:    */   {
/* 135:160 */     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(atributo);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static Usuario getUsuarioEnSesion()
/* 139:    */   {
/* 140:169 */     Usuario usuarioEnSesion = null;
/* 141:170 */     Object objectUsuarioEnSesion = getAtributo("com.asinfo.as2.usuario");
/* 142:172 */     if (objectUsuarioEnSesion != null) {
/* 143:173 */       usuarioEnSesion = (Usuario)objectUsuarioEnSesion;
/* 144:    */     } else {
/* 145:175 */       usuarioEnSesion = new Usuario("usuario_anonimo");
/* 146:    */     }
/* 147:178 */     if ((getOrganizacion() != null) && (usuarioEnSesion.getListaBodega() == null))
/* 148:    */     {
/* 149:179 */       usuarioEnSesion.setListaBodega(new ArrayList());
/* 150:180 */       for (UsuarioBodega usuarioBodega : usuarioEnSesion.getListaUsuarioBodega()) {
/* 151:181 */         if (usuarioBodega.getBodega().getIdOrganizacion() == getOrganizacion().getId()) {
/* 152:182 */           usuarioEnSesion.getListaBodega().add(usuarioBodega.getBodega());
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:187 */     return usuarioEnSesion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static Organizacion getOrganizacion()
/* 160:    */   {
/* 161:197 */     Organizacion organizacion = (Organizacion)getAtributo("com.asinfo.as2.organizacion");
/* 162:198 */     return organizacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static Sucursal getSucursal()
/* 166:    */   {
/* 167:208 */     Sucursal sucursal = (Sucursal)getAtributo("com.asinfo.as2.sucursal");
/* 168:209 */     return sucursal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static PuntoDeVenta getPuntoDeVenta()
/* 172:    */   {
/* 173:219 */     PuntoDeVenta puntoDeVenta = (PuntoDeVenta)getAtributo("com.asinfo.as2.punto_de_venta");
/* 174:220 */     return puntoDeVenta;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static Caja getCaja()
/* 178:    */   {
/* 179:230 */     Caja caja = (Caja)getAtributo("com.asinfo.as2.caja");
/* 180:231 */     return caja;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static Bodega getBodega()
/* 184:    */   {
/* 185:235 */     Bodega bodega = (Bodega)getAtributo("bodega");
/* 186:236 */     return bodega;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static String getHostRemoto()
/* 190:    */   {
/* 191:246 */     return (String)getAtributo("host_remoro");
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static String getDireccionMatriz()
/* 195:    */   {
/* 196:250 */     return (String)getAtributo("direccion_matriz");
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static String obtenerDirectorioRaiz()
/* 200:    */     throws ExcepcionAS2
/* 201:    */   {
/* 202:261 */     String nombreDirectorio = ServicioConfiguracion.AS2_HOME;
/* 203:263 */     if (nombreDirectorio == null) {
/* 204:264 */       throw new ExcepcionAS2("La variable del entorno AS2_HOME no esta configurada");
/* 205:    */     }
/* 206:267 */     return nombreDirectorio;
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.AppUtil
 * JD-Core Version:    0.7.0.1
 */