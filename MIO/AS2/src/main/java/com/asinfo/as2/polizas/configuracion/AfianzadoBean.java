/*   1:    */ package com.asinfo.as2.polizas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Ciudad;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  10:    */ import com.asinfo.as2.entities.polizas.Afianzado;
/*  11:    */ import com.asinfo.as2.polizas.configuracion.servicio.ServicioAfianzado;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
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
/*  24:    */ import org.primefaces.model.TreeNode;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class AfianzadoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioAfianzado servicioAfianzado;
/*  34:    */   @EJB
/*  35:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  36:    */   private Afianzado afianzado;
/*  37:    */   private TreeNode selectedNode;
/*  38:    */   private LazyDataModel<Afianzado> listaAfianzado;
/*  39:    */   private List<TipoIdentificacion> listaTipoIdentificacion;
/*  40:    */   private DataTable dtAfianzado;
/*  41:    */   
/*  42:    */   @PostConstruct
/*  43:    */   public void init()
/*  44:    */   {
/*  45: 79 */     this.listaAfianzado = new LazyDataModel()
/*  46:    */     {
/*  47:    */       private static final long serialVersionUID = 1L;
/*  48:    */       
/*  49:    */       public List<Afianzado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  50:    */       {
/*  51: 86 */         List<Afianzado> lista = new ArrayList();
/*  52: 87 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53:    */         
/*  54: 89 */         lista = AfianzadoBean.this.servicioAfianzado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  55:    */         
/*  56: 91 */         AfianzadoBean.this.listaAfianzado.setRowCount(AfianzadoBean.this.servicioAfianzado.contarPorCriterio(filters));
/*  57: 92 */         return lista;
/*  58:    */       }
/*  59:    */     };
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void crearAfianzado()
/*  63:    */   {
/*  64:106 */     this.afianzado = new Afianzado();
/*  65:107 */     this.afianzado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  66:108 */     this.afianzado.setIdSucursal(AppUtil.getSucursal().getId());
/*  67:109 */     this.afianzado.setTipoIdentificacion(new TipoIdentificacion());
/*  68:110 */     this.afianzado.setCiudad(new Ciudad());
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String editar()
/*  72:    */   {
/*  73:119 */     if (getAfianzado().getIdAfianzado() > 0)
/*  74:    */     {
/*  75:120 */       this.afianzado = this.servicioAfianzado.cargarDetalle(getAfianzado().getIdAfianzado());
/*  76:121 */       setEditado(true);
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  81:    */     }
/*  82:125 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String guardar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:135 */       if (getAfianzado().getCiudad().getId() > 0)
/*  90:    */       {
/*  91:136 */         this.servicioAfianzado.guardar(getAfianzado());
/*  92:137 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  93:138 */         setEditado(false);
/*  94:139 */         limpiar();
/*  95:    */       }
/*  96:    */       else
/*  97:    */       {
/*  98:141 */         addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar_ciudad"));
/*  99:    */       }
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 104:145 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:    */     }
/* 106:147 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String eliminar()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:157 */       this.servicioAfianzado.eliminar(getAfianzado());
/* 114:158 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:160 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 119:161 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 120:    */     }
/* 121:163 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String cargarDatos()
/* 125:    */   {
/* 126:172 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String limpiar()
/* 130:    */   {
/* 131:181 */     crearAfianzado();
/* 132:182 */     return "";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void cargarCiudad()
/* 136:    */   {
/* 137:    */     try
/* 138:    */     {
/* 139:188 */       Ciudad ciudad = (Ciudad)this.selectedNode.getData();
/* 140:189 */       getAfianzado().setCiudad(ciudad);
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:192 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Afianzado getAfianzado()
/* 149:    */   {
/* 150:211 */     if (this.afianzado == null) {
/* 151:212 */       crearAfianzado();
/* 152:    */     }
/* 153:214 */     return this.afianzado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setAfianzado(Afianzado afianzado)
/* 157:    */   {
/* 158:224 */     this.afianzado = afianzado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public LazyDataModel<Afianzado> getListaAfianzado()
/* 162:    */   {
/* 163:233 */     return this.listaAfianzado;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaAfianzado(LazyDataModel<Afianzado> listaAfianzado)
/* 167:    */   {
/* 168:243 */     this.listaAfianzado = listaAfianzado;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<TipoIdentificacion> getListaTipoIdentificacion()
/* 172:    */   {
/* 173:252 */     if (this.listaTipoIdentificacion == null) {
/* 174:253 */       this.listaTipoIdentificacion = this.servicioTipoIdentificacion.obtenerListaCombo("nombre", true, null);
/* 175:    */     }
/* 176:255 */     return this.listaTipoIdentificacion;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setListaTipoIdentificacion(List<TipoIdentificacion> listaTipoIdentificacion)
/* 180:    */   {
/* 181:265 */     this.listaTipoIdentificacion = listaTipoIdentificacion;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public DataTable getDtAfianzado()
/* 185:    */   {
/* 186:274 */     return this.dtAfianzado;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setDtAfianzado(DataTable dtAfianzado)
/* 190:    */   {
/* 191:284 */     this.dtAfianzado = dtAfianzado;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public TreeNode getSelectedNode()
/* 195:    */   {
/* 196:293 */     return this.selectedNode;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setSelectedNode(TreeNode selectedNode)
/* 200:    */   {
/* 201:303 */     this.selectedNode = selectedNode;
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.polizas.configuracion.AfianzadoBean
 * JD-Core Version:    0.7.0.1
 */