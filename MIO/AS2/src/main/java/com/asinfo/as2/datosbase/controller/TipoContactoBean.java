/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoContacto;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class TipoContactoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28: 56 */   private static List<String> listaVariablesTextoCorreoFactura = new ArrayList()
/*  29:    */   {
/*  30:    */     private static final long serialVersionUID = 1L;
/*  31:    */   };
/*  32: 67 */   private static List<String> listaVariablesTextoCorreoNotaCredito = new ArrayList()
/*  33:    */   {
/*  34:    */     private static final long serialVersionUID = 1L;
/*  35:    */   };
/*  36: 77 */   private static List<String> listaVariablesTextoCorreoNotaDebito = new ArrayList()
/*  37:    */   {
/*  38:    */     private static final long serialVersionUID = 1L;
/*  39:    */   };
/*  40: 87 */   private static List<String> listaVariablesTextoCorreoRetencion = new ArrayList()
/*  41:    */   {
/*  42:    */     private static final long serialVersionUID = 1L;
/*  43:    */   };
/*  44: 97 */   private static List<String> listaVariablesTextoCorreoGuiaRemision = new ArrayList()
/*  45:    */   {
/*  46:    */     private static final long serialVersionUID = 1L;
/*  47:    */   };
/*  48:109 */   private static List<String> listaVariablesTextoCorreoPedidoCliente = new ArrayList()
/*  49:    */   {
/*  50:    */     private static final long serialVersionUID = 1L;
/*  51:    */   };
/*  52:119 */   private static List<String> listaVariablesTextoCorreoPedidoProveedor = new ArrayList()
/*  53:    */   {
/*  54:    */     private static final long serialVersionUID = 1L;
/*  55:    */   };
/*  56:131 */   private static List<String> listaVariablesTextoCorreoPagoProveedor = new ArrayList()
/*  57:    */   {
/*  58:    */     private static final long serialVersionUID = 1L;
/*  59:    */   };
/*  60:141 */   private static List<String> listaVariablesTextoCorreoVencimientoFacturaCliente = new ArrayList()
/*  61:    */   {
/*  62:    */     private static final long serialVersionUID = 1L;
/*  63:    */   };
/*  64:150 */   private static List<String> listaVariablesTextoCorreoSolicitudCompra = new ArrayList()
/*  65:    */   {
/*  66:    */     private static final long serialVersionUID = 1L;
/*  67:    */   };
/*  68:    */   @EJB
/*  69:    */   private ServicioTipoContacto servicioTipoContacto;
/*  70:    */   private TipoContacto tipoContacto;
/*  71:    */   private LazyDataModel<TipoContacto> listaTipoContacto;
/*  72:    */   private DataTable dtTipoContacto;
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77:176 */     this.listaTipoContacto = new LazyDataModel()
/*  78:    */     {
/*  79:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  80:    */       
/*  81:    */       public List<TipoContacto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  82:    */       {
/*  83:183 */         List<TipoContacto> lista = new ArrayList();
/*  84:184 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  85:    */         
/*  86:186 */         lista = TipoContactoBean.this.servicioTipoContacto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  87:    */         
/*  88:188 */         TipoContactoBean.this.listaTipoContacto.setRowCount(TipoContactoBean.this.servicioTipoContacto.contarPorCriterio(filters));
/*  89:189 */         return lista;
/*  90:    */       }
/*  91:    */     };
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String editar()
/*  95:    */   {
/*  96:202 */     if ((getTipoContacto() != null) && (getTipoContacto().getId() != 0)) {
/*  97:203 */       setEditado(true);
/*  98:    */     } else {
/*  99:205 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 100:    */     }
/* 101:208 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String guardar()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:221 */       this.servicioTipoContacto.guardar(this.tipoContacto);
/* 109:    */       
/* 110:223 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 111:224 */       limpiar();
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:226 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 116:227 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 117:    */     }
/* 118:229 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String eliminar()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:240 */       this.servicioTipoContacto.eliminar(this.tipoContacto);
/* 126:241 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 127:    */     }
/* 128:    */     catch (Exception e)
/* 129:    */     {
/* 130:243 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 131:244 */       LOG.error("ERROR AL ELMINAR DATOS", e);
/* 132:    */     }
/* 133:246 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String limpiar()
/* 137:    */   {
/* 138:257 */     setEditado(false);
/* 139:    */     
/* 140:259 */     this.tipoContacto = new TipoContacto();
/* 141:260 */     this.tipoContacto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 142:261 */     this.tipoContacto.setIdSucursal(AppUtil.getSucursal().getId());
/* 143:262 */     this.tipoContacto.setActivo(true);
/* 144:    */     
/* 145:264 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String cargarDatos()
/* 149:    */   {
/* 150:274 */     return "";
/* 151:    */   }
/* 152:    */   
/* 153:    */   public TipoContacto getTipoContacto()
/* 154:    */   {
/* 155:283 */     return this.tipoContacto;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setTipoContacto(TipoContacto tipoContacto)
/* 159:    */   {
/* 160:293 */     this.tipoContacto = tipoContacto;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public LazyDataModel<TipoContacto> getListaTipoContacto()
/* 164:    */   {
/* 165:302 */     return this.listaTipoContacto;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setListaTipoContacto(LazyDataModel<TipoContacto> listaTipoContacto)
/* 169:    */   {
/* 170:312 */     this.listaTipoContacto = listaTipoContacto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public DataTable getDtTipoContacto()
/* 174:    */   {
/* 175:321 */     return this.dtTipoContacto;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setDtTipoContacto(DataTable dtTipoContacto)
/* 179:    */   {
/* 180:331 */     this.dtTipoContacto = dtTipoContacto;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<String> getListaVariablesTextoCorreoFactura()
/* 184:    */   {
/* 185:335 */     return listaVariablesTextoCorreoFactura;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<String> getListaVariablesTextoCorreoNotaCredito()
/* 189:    */   {
/* 190:339 */     return listaVariablesTextoCorreoNotaCredito;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<String> getListaVariablesTextoCorreoNotaDebito()
/* 194:    */   {
/* 195:343 */     return listaVariablesTextoCorreoNotaDebito;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<String> getListaVariablesTextoCorreoRetencion()
/* 199:    */   {
/* 200:347 */     return listaVariablesTextoCorreoRetencion;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<String> getListaVariablesTextoCorreoGuiaRemision()
/* 204:    */   {
/* 205:351 */     return listaVariablesTextoCorreoGuiaRemision;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<String> getListaVariablesTextoCorreoPedidoCliente()
/* 209:    */   {
/* 210:355 */     return listaVariablesTextoCorreoPedidoCliente;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<String> getListaVariablesTextoCorreoPedidoProveedor()
/* 214:    */   {
/* 215:359 */     return listaVariablesTextoCorreoPedidoProveedor;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<String> getListaVariablesTextoCorreoPagoProveedor()
/* 219:    */   {
/* 220:363 */     return listaVariablesTextoCorreoPagoProveedor;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<String> getListaVariablesTextoCorreoVencimientoFacturaCliente()
/* 224:    */   {
/* 225:367 */     return listaVariablesTextoCorreoVencimientoFacturaCliente;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<String> getListaVariablesTextoCorreoSolicitudCompra()
/* 229:    */   {
/* 230:371 */     return listaVariablesTextoCorreoSolicitudCompra;
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.TipoContactoBean
 * JD-Core Version:    0.7.0.1
 */