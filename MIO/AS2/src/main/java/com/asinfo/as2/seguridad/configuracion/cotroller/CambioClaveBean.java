/*   1:    */ package com.asinfo.as2.seguridad.configuracion.cotroller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.UsuarioOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   9:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  10:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class CambioClaveBean
/*  28:    */   extends PageController
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioUsuario servicioUsuario;
/*  33:    */   @EJB
/*  34:    */   private ServicioOrganizacion servicioOrganizacion;
/*  35:    */   private EntidadUsuario usuario;
/*  36:    */   private EntidadUsuario usuarioSeleccionado;
/*  37:    */   private String clave;
/*  38:    */   private String claveVerificadora;
/*  39:    */   private LazyDataModel<EntidadUsuario> listaUsuario;
/*  40:    */   private DataTable dtUsuario;
/*  41:    */   
/*  42:    */   @PostConstruct
/*  43:    */   public void init()
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47: 77 */       this.listaUsuario = new LazyDataModel()
/*  48:    */       {
/*  49:    */         private static final long serialVersionUID = 101L;
/*  50:    */         
/*  51:    */         public List<EntidadUsuario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */         {
/*  53: 86 */           List<EntidadUsuario> lista = new ArrayList();
/*  54:    */           
/*  55: 88 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56:    */           
/*  57: 90 */           List<Integer> listaIdOrganizacion = new ArrayList();
/*  58:    */           Iterator localIterator;
/*  59:    */           UsuarioOrganizacion usuarioOrganizacion;
/*  60: 92 */           if (!AppUtil.getUsuarioEnSesion().isIndicadorSuperAdministrador())
/*  61:    */           {
/*  62: 93 */             for (localIterator = AppUtil.getUsuarioEnSesion().getListaUsuarioOrganizacion().iterator(); localIterator.hasNext();)
/*  63:    */             {
/*  64: 93 */               usuarioOrganizacion = (UsuarioOrganizacion)localIterator.next();
/*  65: 94 */               listaIdOrganizacion.add(Integer.valueOf(usuarioOrganizacion.getOrganizacion().getId()));
/*  66:    */             }
/*  67:    */           }
/*  68:    */           else
/*  69:    */           {
/*  70: 97 */             Object listaOrganizacion = CambioClaveBean.this.servicioOrganizacion.obtenerListaCombo(null, true, null);
/*  71: 98 */             for (Organizacion organizacion : (List)listaOrganizacion) {
/*  72: 99 */               listaIdOrganizacion.add(Integer.valueOf(organizacion.getId()));
/*  73:    */             }
/*  74:    */           }
/*  75:103 */           if (!AppUtil.getUsuarioEnSesion().isIndicadorAdministrador()) {
/*  76:104 */             filters.put("nombreUsuario", "" + AppUtil.getUsuarioEnSesion().getNombreUsuario());
/*  77:    */           }
/*  78:107 */           lista = CambioClaveBean.this.servicioUsuario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters, listaIdOrganizacion);
/*  79:108 */           CambioClaveBean.this.listaUsuario.setRowCount(CambioClaveBean.this.servicioUsuario.contarPorCriterio(filters, listaIdOrganizacion));
/*  80:    */           
/*  81:110 */           return lista;
/*  82:    */         }
/*  83:    */       };
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:114 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  88:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  89:    */     }
/*  90:117 */     limpiar();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String cargarListaUsuario()
/*  94:    */   {
/*  95:124 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String cargarUsuario()
/*  99:    */   {
/* 100:128 */     setUsuario(this.usuarioSeleccionado);
/* 101:129 */     this.usuarioSeleccionado = null;
/* 102:130 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String guardar()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:136 */       if (getUsuario().getId() > 0)
/* 110:    */       {
/* 111:137 */         String claveEncriptada = AppUtil.encriptaEnMD5(getClave());
/* 112:138 */         getUsuario().setClave(claveEncriptada);
/* 113:139 */         this.servicioUsuario.actualizarClave(getUsuario());
/* 114:140 */         limpiar();
/* 115:141 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:144 */         addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar_usuario"));
/* 120:    */       }
/* 121:    */     }
/* 122:    */     catch (Exception e)
/* 123:    */     {
/* 124:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 125:149 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 126:    */     }
/* 127:151 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void limpiar()
/* 131:    */   {
/* 132:155 */     this.usuario = new EntidadUsuario();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public EntidadUsuario getUsuario()
/* 136:    */   {
/* 137:168 */     if (this.usuario == null) {
/* 138:169 */       this.usuario = new EntidadUsuario();
/* 139:    */     }
/* 140:171 */     return this.usuario;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setUsuario(EntidadUsuario usuario)
/* 144:    */   {
/* 145:181 */     this.usuario = usuario;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public EntidadUsuario getUsuarioSeleccionado()
/* 149:    */   {
/* 150:190 */     return this.usuarioSeleccionado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setUsuarioSeleccionado(EntidadUsuario usuarioSeleccionado)
/* 154:    */   {
/* 155:200 */     this.usuarioSeleccionado = usuarioSeleccionado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public LazyDataModel<EntidadUsuario> getListaUsuario()
/* 159:    */   {
/* 160:209 */     if (this.listaUsuario == null) {
/* 161:210 */       cargarListaUsuario();
/* 162:    */     }
/* 163:212 */     return this.listaUsuario;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getClave()
/* 167:    */   {
/* 168:221 */     return this.clave;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setClave(String clave)
/* 172:    */   {
/* 173:231 */     this.clave = clave;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getClaveVerificadora()
/* 177:    */   {
/* 178:240 */     return this.claveVerificadora;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setClaveVerificadora(String claveVerificadora)
/* 182:    */   {
/* 183:250 */     this.claveVerificadora = claveVerificadora;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaUsuario(LazyDataModel<EntidadUsuario> listaUsuario)
/* 187:    */   {
/* 188:260 */     this.listaUsuario = listaUsuario;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public DataTable getDtUsuario()
/* 192:    */   {
/* 193:269 */     return this.dtUsuario;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setDtUsuario(DataTable dtUsuario)
/* 197:    */   {
/* 198:279 */     this.dtUsuario = dtUsuario;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getNumeroFilasPorPagina()
/* 202:    */   {
/* 203:289 */     return "10,20,30,40,50,100,200,300,400,500,1000,2000,3000,4000,5000";
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.configuracion.cotroller.CambioClaveBean
 * JD-Core Version:    0.7.0.1
 */