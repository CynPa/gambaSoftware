/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  12:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.JsfUtil;
/*  15:    */ import com.asinfo.as2.utils.comparator.SelectItemComparator;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Arrays;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class AprobarOrdenSalidaMaterialBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  35:    */   @EJB
/*  36:    */   protected transient ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  37:    */   @EJB
/*  38:    */   protected transient ServicioUsuario servicioUsuario;
/*  39:    */   protected Integer idOrdenSalidaMaterial;
/*  40:    */   protected OrdenSalidaMaterial ordenSalidaMaterial;
/*  41:    */   protected LazyDataModel<OrdenSalidaMaterial> listaOrdenSalidaMaterial;
/*  42:    */   protected SelectItem[] listaTipoCicloProduccionItem;
/*  43:    */   private boolean aprobarOrden;
/*  44:    */   private DataTable dtOrden;
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 87 */       this.servicioUsuario.verificarJerarquiaUsuario(AppUtil.getUsuarioEnSesion(), DocumentoBase.ORDEN_SALIDA_MATERIAL);
/*  52:    */     }
/*  53:    */     catch (AS2Exception e)
/*  54:    */     {
/*  55: 89 */       JsfUtil.addErrorMessage(e, "");
/*  56: 90 */       e.printStackTrace();
/*  57:    */     }
/*  58: 92 */     this.listaOrdenSalidaMaterial = new LazyDataModel()
/*  59:    */     {
/*  60:    */       private static final long serialVersionUID = 1L;
/*  61:    */       
/*  62:    */       public List<OrdenSalidaMaterial> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  63:    */       {
/*  64: 98 */         String numero = (String)filters.get("numero");
/*  65:    */         
/*  66:100 */         TipoCicloProduccionEnum tipoCicloProduccionEnum = TipoCicloProduccionEnum.obtenerTipoCicloProduccion((String)filters.get("tipoCicloProduccionEnum"));
/*  67:101 */         String sucursal = (String)filters.get("sucursal.nombre");
/*  68:102 */         Estado estado = Estado.obtenerEstado((String)filters.get("estado"));
/*  69:103 */         String descripcion = (String)filters.get("descripcion");
/*  70:104 */         List<OrdenSalidaMaterial> lista = new ArrayList();
/*  71:    */         try
/*  72:    */         {
/*  73:106 */           lista = AprobarOrdenSalidaMaterialBean.this.servicioOrdenSalidaMaterial.getOrdenSalidaMaterialPorAprobar(AppUtil.getOrganizacion().getId(), 
/*  74:107 */             AppUtil.getUsuarioEnSesion(), AprobarOrdenSalidaMaterialBean.this.idOrdenSalidaMaterial, numero, tipoCicloProduccionEnum, sucursal, estado, descripcion);
/*  75:    */         }
/*  76:    */         catch (AS2Exception e)
/*  77:    */         {
/*  78:109 */           JsfUtil.addErrorMessage(e, "");
/*  79:110 */           e.printStackTrace();
/*  80:    */         }
/*  81:112 */         AprobarOrdenSalidaMaterialBean.this.listaOrdenSalidaMaterial.setRowCount(lista.size());
/*  82:113 */         return lista;
/*  83:    */       }
/*  84:    */     };
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String editar()
/*  88:    */   {
/*  89:124 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  90:125 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String guardar()
/*  94:    */   {
/*  95:130 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  96:131 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String crear()
/* 100:    */   {
/* 101:136 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 102:137 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String eliminar()
/* 106:    */   {
/* 107:142 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 108:143 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:152 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String limpiar()
/* 117:    */   {
/* 118:161 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void ordenSeleccionado(boolean indicador)
/* 122:    */   {
/* 123:165 */     this.ordenSalidaMaterial = ((OrdenSalidaMaterial)this.dtOrden.getRowData());
/* 124:166 */     setAprobarOrden(indicador);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void aprobarDesaprobarOrdenSalidaMaterial()
/* 128:    */   {
/* 129:170 */     if ((getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().getIdOrdenSalidaMaterial() != 0))
/* 130:    */     {
/* 131:172 */       OrdenSalidaMaterial osm = this.servicioOrdenSalidaMaterial.buscarPorId(getOrdenSalidaMaterial().getId());
/* 132:173 */       List<String> lista = this.servicioOrdenSalidaMaterial.ordenSalidaMaterialEnConsumoBodega(getOrdenSalidaMaterial());
/* 133:174 */       if ((lista.size() > 0) && (getOrdenSalidaMaterial().isAprobado()))
/* 134:    */       {
/* 135:175 */         String numero = "";
/* 136:176 */         for (String s : lista) {
/* 137:177 */           numero = numero + " " + s;
/* 138:    */         }
/* 139:179 */         addErrorMessage(getLanguageController().getMensaje("msg_orden_salida_material_en_comsumo_bodega") + numero);
/* 140:    */       }
/* 141:    */       else
/* 142:    */       {
/* 143:181 */         if ((osm.getEstado().equals(Estado.ANULADO)) || (osm.getEstado().equals(Estado.PROCESADO)) || (osm.getEstado().equals(Estado.CERRADO)))
/* 144:    */         {
/* 145:182 */           addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:184 */           this.servicioOrdenSalidaMaterial.aprobarDesaprobarOrdenSalidaMaterial(this.ordenSalidaMaterial, isAprobarOrden());
/* 150:185 */           addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 151:    */         }
/* 152:187 */         this.dtOrden.reset();
/* 153:    */       }
/* 154:    */     }
/* 155:    */     else
/* 156:    */     {
/* 157:190 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public LazyDataModel<OrdenSalidaMaterial> getListaOrdenSalidaMaterial()
/* 162:    */   {
/* 163:201 */     return this.listaOrdenSalidaMaterial;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaOrdenSalidaMaterial(LazyDataModel<OrdenSalidaMaterial> listaOrdenSalidaMaterial)
/* 167:    */   {
/* 168:211 */     this.listaOrdenSalidaMaterial = listaOrdenSalidaMaterial;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 172:    */   {
/* 173:215 */     return this.ordenSalidaMaterial;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 177:    */   {
/* 178:219 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public DataTable getDtOrden()
/* 182:    */   {
/* 183:228 */     return this.dtOrden;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setDtOrden(DataTable dtDetalleOrden)
/* 187:    */   {
/* 188:238 */     this.dtOrden = dtDetalleOrden;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public SelectItem[] getListaTipoCicloProduccionItem()
/* 192:    */   {
/* 193:247 */     if (this.listaTipoCicloProduccionItem == null)
/* 194:    */     {
/* 195:249 */       List<SelectItem> lista = new ArrayList();
/* 196:250 */       lista.add(new SelectItem("", ""));
/* 197:252 */       for (TipoCicloProduccionEnum t : TipoCicloProduccionEnum.values())
/* 198:    */       {
/* 199:253 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 200:254 */         lista.add(item);
/* 201:    */       }
/* 202:256 */       this.listaTipoCicloProduccionItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 203:    */     }
/* 204:259 */     Arrays.sort(this.listaTipoCicloProduccionItem, new SelectItemComparator());
/* 205:    */     
/* 206:261 */     return this.listaTipoCicloProduccionItem;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isAprobarOrden()
/* 210:    */   {
/* 211:265 */     return this.aprobarOrden;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setAprobarOrden(boolean aprobarOrden)
/* 215:    */   {
/* 216:269 */     this.aprobarOrden = aprobarOrden;
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.AprobarOrdenSalidaMaterialBean
 * JD-Core Version:    0.7.0.1
 */