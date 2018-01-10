/*   1:    */ package com.asinfo.as2.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Organizacion;
/*   4:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*   5:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   6:    */ import com.asinfo.as2.seguridad.ServicioProceso;
/*   7:    */ import com.asinfo.as2.seguridad.ServicioSistema;
/*   8:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   9:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.SessionScoped;
/*  19:    */ import javax.faces.context.ExternalContext;
/*  20:    */ import javax.faces.context.FacesContext;
/*  21:    */ import javax.servlet.http.HttpServletRequest;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.model.menu.DefaultMenuItem;
/*  24:    */ import org.primefaces.model.menu.DefaultMenuModel;
/*  25:    */ import org.primefaces.model.menu.DefaultSubMenu;
/*  26:    */ import org.primefaces.model.menu.MenuModel;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @SessionScoped
/*  30:    */ public class MenuBean
/*  31:    */ {
/*  32:    */   @EJB
/*  33:    */   private ServicioUsuario servicioUsuario;
/*  34:    */   @EJB
/*  35:    */   private ServicioSistema servicioSistema;
/*  36:    */   @EJB
/*  37:    */   private ServicioProceso servicioProceso;
/*  38:    */   static final String nombreSistema = "AS2-ERP";
/*  39: 60 */   private MenuModel menu = new DefaultMenuModel();
/*  40: 61 */   private String filtroProceso = new String("");
/*  41:    */   private List<EntidadProceso> listaProcesos;
/*  42:    */   private EntidadProceso entidadProceso;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 69 */     generarMenu();
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void generarMenu()
/*  51:    */   {
/*  52: 75 */     Usuario usuario = AppUtil.getUsuarioEnSesion();
/*  53: 76 */     Organizacion organizacion = AppUtil.getOrganizacion();
/*  54:    */     
/*  55: 78 */     EntidadSistema sistema = this.servicioSistema.buscarPorNombre("AS2-ERP");
/*  56: 79 */     this.listaProcesos = this.servicioUsuario.getProcesos(usuario, sistema, organizacion);
/*  57: 80 */     HashMap<String, DefaultSubMenu> hmMenuAS2 = new HashMap();
/*  58: 83 */     for (EntidadProceso proceso : this.listaProcesos)
/*  59:    */     {
/*  60: 85 */       String url = proceso.getViewId();
/*  61: 86 */       url = url.replace("/paginas/", "");
/*  62:    */       
/*  63: 88 */       String[] paths = url.split("/");
/*  64:    */       
/*  65: 90 */       String path = "";
/*  66: 91 */       String pathPadre = "";
/*  67: 93 */       for (String label : paths)
/*  68:    */       {
/*  69: 94 */         if (label.startsWith("?"))
/*  70:    */         {
/*  71: 95 */           int posicionPrimeraOcurrencia = url.indexOf('?');
/*  72: 96 */           label = url.substring(posicionPrimeraOcurrencia);
/*  73:    */         }
/*  74:    */         else
/*  75:    */         {
/*  76: 98 */           label = label.substring(0, 1).toUpperCase() + label.substring(1);
/*  77:    */         }
/*  78:101 */         path = path + label;
/*  79:103 */         if (pathPadre.isEmpty())
/*  80:    */         {
/*  81:105 */           if (!hmMenuAS2.containsKey(path)) {
/*  82:107 */             if (path.contains(".xhtml"))
/*  83:    */             {
/*  84:108 */               DefaultMenuItem mi = new DefaultMenuItem();
/*  85:109 */               mi.setValue(proceso.getViewName());
/*  86:110 */               mi.setUrl(proceso.getViewId().replace(".xhtml", ".jsf"));
/*  87:111 */               mi.setId(path.replace(".xhtml", ""));
/*  88:112 */               this.menu.addElement(mi);
/*  89:    */             }
/*  90:    */             else
/*  91:    */             {
/*  92:113 */               if (label.startsWith("?"))
/*  93:    */               {
/*  94:114 */                 DefaultMenuItem mi = new DefaultMenuItem();
/*  95:115 */                 mi.setValue(proceso.getViewName());
/*  96:116 */                 mi.setUrl(label.substring(1));
/*  97:117 */                 mi.setId(path);
/*  98:118 */                 mi.setTarget("_blank");
/*  99:119 */                 this.menu.addElement(mi);
/* 100:120 */                 break;
/* 101:    */               }
/* 102:122 */               DefaultSubMenu submenu = new DefaultSubMenu();
/* 103:123 */               submenu.setLabel(label);
/* 104:124 */               submenu.setId(path);
/* 105:125 */               hmMenuAS2.put(path, submenu);
/* 106:126 */               this.menu.addElement(submenu);
/* 107:    */             }
/* 108:    */           }
/* 109:    */         }
/* 110:130 */         else if (hmMenuAS2.containsKey(pathPadre))
/* 111:    */         {
/* 112:131 */           DefaultSubMenu smPadre = (DefaultSubMenu)hmMenuAS2.get(pathPadre);
/* 113:133 */           if (path.contains(".xhtml"))
/* 114:    */           {
/* 115:134 */             DefaultMenuItem mi = new DefaultMenuItem();
/* 116:135 */             mi.setValue(proceso.getViewName());
/* 117:136 */             mi.setUrl(proceso.getViewId().replace(".xhtml", ".jsf"));
/* 118:137 */             mi.setId(path.replace(".xhtml", ""));
/* 119:138 */             smPadre.addElement(mi);
/* 120:    */           }
/* 121:    */           else
/* 122:    */           {
/* 123:140 */             if (label.startsWith("?"))
/* 124:    */             {
/* 125:141 */               DefaultMenuItem mi = new DefaultMenuItem();
/* 126:142 */               mi.setValue(proceso.getViewName());
/* 127:143 */               mi.setUrl(label.substring(1));
/* 128:144 */               mi.setId(path);
/* 129:145 */               mi.setTarget("_blank");
/* 130:146 */               smPadre.addElement(mi);
/* 131:147 */               break;
/* 132:    */             }
/* 133:148 */             if (!hmMenuAS2.containsKey(path))
/* 134:    */             {
/* 135:149 */               DefaultSubMenu sm = new DefaultSubMenu();
/* 136:150 */               sm.setLabel(label);
/* 137:151 */               sm.setId(path);
/* 138:152 */               smPadre.addElement(sm);
/* 139:153 */               hmMenuAS2.put(path, sm);
/* 140:    */             }
/* 141:    */           }
/* 142:    */         }
/* 143:157 */         pathPadre = path;
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<EntidadProceso> autocompletarProceso(String consulta)
/* 149:    */   {
/* 150:164 */     List<EntidadProceso> lista = new ArrayList();
/* 151:    */     int contador;
/* 152:166 */     if (consulta.length() >= 2)
/* 153:    */     {
/* 154:167 */       contador = 1;
/* 155:169 */       for (EntidadProceso proceso : this.listaProcesos) {
/* 156:171 */         if (proceso.getViewName().toUpperCase().contains(consulta.toUpperCase()))
/* 157:    */         {
/* 158:173 */           if (contador++ > 20) {
/* 159:    */             break;
/* 160:    */           }
/* 161:174 */           lista.add(proceso);
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:183 */     return lista;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void seleccionarProceso(SelectEvent event)
/* 169:    */   {
/* 170:188 */     EntidadProceso proceso = (EntidadProceso)event.getObject();
/* 171:189 */     ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
/* 172:190 */     HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
/* 173:    */     try
/* 174:    */     {
/* 175:192 */       externalContext.redirect(request.getContextPath() + proceso.getViewId().replace(".xhtml", ".jsf"));
/* 176:    */     }
/* 177:    */     catch (IOException e)
/* 178:    */     {
/* 179:194 */       e.printStackTrace();
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public MenuModel getMenu()
/* 184:    */   {
/* 185:204 */     return this.menu;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setMenu(MenuModel menu)
/* 189:    */   {
/* 190:214 */     this.menu = menu;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<EntidadProceso> getListaProcesos()
/* 194:    */   {
/* 195:218 */     return this.listaProcesos;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setListaProcesos(List<EntidadProceso> listaProcesos)
/* 199:    */   {
/* 200:222 */     this.listaProcesos = listaProcesos;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getFiltroProceso()
/* 204:    */   {
/* 205:226 */     return this.filtroProceso;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setFiltroProceso(String filtroProceso)
/* 209:    */   {
/* 210:230 */     this.filtroProceso = filtroProceso;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public EntidadProceso getEntidadProceso()
/* 214:    */   {
/* 215:237 */     return this.entidadProceso;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setEntidadProceso(EntidadProceso entidadProceso)
/* 219:    */   {
/* 220:244 */     this.entidadProceso = entidadProceso;
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.controller.MenuBean
 * JD-Core Version:    0.7.0.1
 */