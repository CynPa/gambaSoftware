/*   1:    */ package com.asinfo.as2.seguridad.modelo;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.Dispositivo;
/*   5:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*   6:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.UsuarioSucursal;
/*   8:    */ import com.asinfo.as2.entities.Visualizacion;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadRol;
/*  10:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Collections;
/*  14:    */ import java.util.Comparator;
/*  15:    */ import java.util.List;
/*  16:    */ 
/*  17:    */ public class Usuario
/*  18:    */ {
/*  19:    */   private int idUsuario;
/*  20:    */   private String nombreUsuario;
/*  21:    */   private String nombre1;
/*  22:    */   private String nombre2;
/*  23:    */   private String tema;
/*  24:    */   private boolean indicadorNuevo;
/*  25:    */   private boolean indicadorSuperAdministrador;
/*  26:    */   private boolean indicadorUsuarioPos;
/*  27:    */   private boolean indicadorAdministrador;
/*  28:    */   private boolean indicadorAprobador;
/*  29:    */   private TipoVisualizacionEnum tipoVisualizacion;
/*  30:    */   private Dispositivo dispositivo;
/*  31:    */   private Visualizacion visualizacionUsuario;
/*  32: 50 */   private List<Rol> listaRol = new ArrayList();
/*  33: 51 */   private List<UsuarioBodega> listaUsuarioBodega = new ArrayList();
/*  34: 52 */   private List<UsuarioOrganizacion> listaUsuarioOrganizacion = new ArrayList();
/*  35: 53 */   private List<UsuarioSucursal> listaUsuarioSucursal = new ArrayList();
/*  36:    */   private List<Bodega> listaBodega;
/*  37:    */   
/*  38:    */   public Usuario(EntidadUsuario entidadUsuario)
/*  39:    */   {
/*  40: 58 */     this.idUsuario = entidadUsuario.getIdUsuario();
/*  41: 59 */     this.nombreUsuario = entidadUsuario.getNombreUsuario();
/*  42: 60 */     this.nombre1 = entidadUsuario.getNombre1();
/*  43: 61 */     this.nombre2 = entidadUsuario.getNombre2();
/*  44: 62 */     this.tema = entidadUsuario.getTema();
/*  45: 63 */     this.indicadorNuevo = entidadUsuario.isIndicadorNuevo();
/*  46: 64 */     this.indicadorSuperAdministrador = entidadUsuario.isIndicadorSuperAdministrador();
/*  47: 65 */     this.indicadorUsuarioPos = entidadUsuario.isIndicadorUsuarioPos();
/*  48: 66 */     this.indicadorAdministrador = entidadUsuario.isIndicadorAdministrador();
/*  49: 67 */     this.indicadorAprobador = entidadUsuario.getIndicadorAprobador().booleanValue();
/*  50: 68 */     this.tipoVisualizacion = entidadUsuario.getTipoVisualizacion();
/*  51: 69 */     this.dispositivo = entidadUsuario.getDispositivo();
/*  52: 72 */     for (EntidadRol er : entidadUsuario.getListaRol()) {
/*  53: 73 */       agregarRol(new Rol(er));
/*  54:    */     }
/*  55: 76 */     for (UsuarioBodega usuarioBodega : entidadUsuario.getListaUsuarioBodega()) {
/*  56: 77 */       this.listaUsuarioBodega.add(usuarioBodega);
/*  57:    */     }
/*  58: 80 */     Collections.sort(this.listaUsuarioBodega, new Comparator()
/*  59:    */     {
/*  60:    */       public int compare(UsuarioBodega o1, UsuarioBodega o2)
/*  61:    */       {
/*  62: 83 */         int resultado = 1;
/*  63: 84 */         if ((o1 != null) && (o2 != null)) {
/*  64: 85 */           if (o1.getBodega().getNombre().compareTo(o2.getBodega().getNombre()) > 0) {
/*  65: 86 */             resultado = 1;
/*  66:    */           } else {
/*  67: 88 */             resultado = -1;
/*  68:    */           }
/*  69:    */         }
/*  70: 92 */         return resultado;
/*  71:    */       }
/*  72:    */     });
/*  73: 96 */     for (UsuarioOrganizacion usuarioOrganizacion : entidadUsuario.getListaUsuarioOrganizacion()) {
/*  74: 97 */       this.listaUsuarioOrganizacion.add(usuarioOrganizacion);
/*  75:    */     }
/*  76:100 */     for (UsuarioSucursal usuarioSucursal : entidadUsuario.getListaUsuarioSucursal()) {
/*  77:101 */       this.listaUsuarioSucursal.add(usuarioSucursal);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Usuario(String nombreUsuario)
/*  82:    */   {
/*  83:106 */     this.nombreUsuario = nombreUsuario;
/*  84:107 */     this.idUsuario = 0;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Usuario() {}
/*  88:    */   
/*  89:    */   public void agregarRol(Rol rol)
/*  90:    */   {
/*  91:114 */     this.listaRol.add(rol);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdUsuario()
/*  95:    */   {
/*  96:123 */     return this.idUsuario;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdUsuario(int idUsuario)
/* 100:    */   {
/* 101:133 */     this.idUsuario = idUsuario;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getNombreUsuario()
/* 105:    */   {
/* 106:142 */     return this.nombreUsuario;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setNombreUsuario(String nombreUsuario)
/* 110:    */   {
/* 111:152 */     this.nombreUsuario = nombreUsuario;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getNombre1()
/* 115:    */   {
/* 116:161 */     return this.nombre1;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setNombre1(String nombre1)
/* 120:    */   {
/* 121:171 */     this.nombre1 = nombre1;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getNombre2()
/* 125:    */   {
/* 126:180 */     return this.nombre2;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setNombre2(String nombre2)
/* 130:    */   {
/* 131:190 */     this.nombre2 = nombre2;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public List<Rol> getListaRol()
/* 135:    */   {
/* 136:199 */     return this.listaRol;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setListaRol(List<Rol> listaRol)
/* 140:    */   {
/* 141:209 */     this.listaRol.clear();
/* 142:210 */     this.listaRol.addAll(listaRol);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getTema()
/* 146:    */   {
/* 147:219 */     return this.tema;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setTema(String tema)
/* 151:    */   {
/* 152:229 */     this.tema = tema;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<Bodega> getListaBodega()
/* 156:    */   {
/* 157:238 */     return this.listaBodega;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 161:    */   {
/* 162:248 */     this.listaBodega = listaBodega;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<UsuarioBodega> getListaUsuarioBodega()
/* 166:    */   {
/* 167:257 */     return this.listaUsuarioBodega;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaUsuarioBodega(List<UsuarioBodega> listaUsuarioBodega)
/* 171:    */   {
/* 172:267 */     this.listaUsuarioBodega = listaUsuarioBodega;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isIndicadorNuevo()
/* 176:    */   {
/* 177:271 */     return this.indicadorNuevo;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setIndicadorNuevo(boolean indicadorNuevo)
/* 181:    */   {
/* 182:275 */     this.indicadorNuevo = indicadorNuevo;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isIndicadorSuperAdministrador()
/* 186:    */   {
/* 187:279 */     return this.indicadorSuperAdministrador;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIndicadorSuperAdministrador(boolean indicadorSuperAdministrador)
/* 191:    */   {
/* 192:283 */     this.indicadorSuperAdministrador = indicadorSuperAdministrador;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<UsuarioOrganizacion> getListaUsuarioOrganizacion()
/* 196:    */   {
/* 197:287 */     return this.listaUsuarioOrganizacion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setListaUsuarioOrganizacion(List<UsuarioOrganizacion> listaUsuarioOrganizacion)
/* 201:    */   {
/* 202:291 */     this.listaUsuarioOrganizacion = listaUsuarioOrganizacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean isIndicadorUsuarioPos()
/* 206:    */   {
/* 207:295 */     return this.indicadorUsuarioPos;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setIndicadorUsuarioPos(boolean indicadorUsuarioPos)
/* 211:    */   {
/* 212:299 */     this.indicadorUsuarioPos = indicadorUsuarioPos;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean isIndicadorAdministrador()
/* 216:    */   {
/* 217:306 */     return this.indicadorAdministrador;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setIndicadorAdministrador(boolean indicadorAdministrador)
/* 221:    */   {
/* 222:314 */     this.indicadorAdministrador = indicadorAdministrador;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isIndicadorAprobador()
/* 226:    */   {
/* 227:318 */     return this.indicadorAprobador;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIndicadorAprobador(boolean indicadorAprobador)
/* 231:    */   {
/* 232:322 */     this.indicadorAprobador = indicadorAprobador;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public TipoVisualizacionEnum getTipoVisualizacion()
/* 236:    */   {
/* 237:326 */     return this.tipoVisualizacion;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setTipoVisualizacion(TipoVisualizacionEnum tipoVisualizacion)
/* 241:    */   {
/* 242:330 */     this.tipoVisualizacion = tipoVisualizacion;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public List<UsuarioSucursal> getListaUsuarioSucursal()
/* 246:    */   {
/* 247:334 */     return this.listaUsuarioSucursal;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setListaUsuarioSucursal(List<UsuarioSucursal> listaUsuarioSucursal)
/* 251:    */   {
/* 252:338 */     this.listaUsuarioSucursal = listaUsuarioSucursal;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public Dispositivo getDispositivo()
/* 256:    */   {
/* 257:342 */     return this.dispositivo;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setDispositivo(Dispositivo dispositivo)
/* 261:    */   {
/* 262:346 */     this.dispositivo = dispositivo;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Visualizacion getVisualizacionUsuario()
/* 266:    */   {
/* 267:353 */     return this.visualizacionUsuario;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setVisualizacionUsuario(Visualizacion visualizacionUsuario)
/* 271:    */   {
/* 272:360 */     this.visualizacionUsuario = visualizacionUsuario;
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.modelo.Usuario
 * JD-Core Version:    0.7.0.1
 */