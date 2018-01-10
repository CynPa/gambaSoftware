/*   1:    */ package com.asinfo.as2.finaciero.activos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDepartamento;
/*   7:    */ import com.asinfo.as2.entities.Departamento;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*  11:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo;
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
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class UbicacionActivoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private ServicioUbicacionActivo servicioUbicacionActivo;
/*  33:    */   @EJB
/*  34:    */   private ServicioDepartamento servicioDepartamento;
/*  35:    */   @EJB
/*  36:    */   private ServicioSucursal servicioSucursal;
/*  37:    */   private UbicacionActivo ubicacionActivo;
/*  38:    */   private LazyDataModel<UbicacionActivo> listaUbicacionActivo;
/*  39:    */   private List<Departamento> listaDepartamentoCombo;
/*  40:    */   private List<Sucursal> listaSucursalCombo;
/*  41:    */   private DataTable dtUbicacionActivo;
/*  42:    */   
/*  43:    */   @PostConstruct
/*  44:    */   public void init()
/*  45:    */   {
/*  46: 83 */     this.listaUbicacionActivo = new LazyDataModel()
/*  47:    */     {
/*  48:    */       private static final long serialVersionUID = 1L;
/*  49:    */       
/*  50:    */       public List<UbicacionActivo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  51:    */       {
/*  52: 90 */         List<UbicacionActivo> lista = new ArrayList();
/*  53: 91 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  54:    */         
/*  55: 93 */         lista = UbicacionActivoBean.this.servicioUbicacionActivo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  56:    */         
/*  57: 95 */         UbicacionActivoBean.this.listaUbicacionActivo.setRowCount(UbicacionActivoBean.this.servicioUbicacionActivo.contarPorCriterio(filters));
/*  58:    */         
/*  59: 97 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void crearUbicacionActivo()
/*  65:    */   {
/*  66:111 */     this.ubicacionActivo = new UbicacionActivo();
/*  67:112 */     this.ubicacionActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  68:113 */     this.ubicacionActivo.setSucursal(AppUtil.getSucursal());
/*  69:114 */     this.ubicacionActivo.setDepartamento(new Departamento());
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String editar()
/*  73:    */   {
/*  74:123 */     if (getUbicacionActivo().getIdUbicacionActivo() > 0) {
/*  75:124 */       setEditado(true);
/*  76:    */     } else {
/*  77:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  78:    */     }
/*  79:128 */     return "";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String guardar()
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:138 */       this.servicioUbicacionActivo.guardar(this.ubicacionActivo);
/*  87:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  88:140 */       setEditado(false);
/*  89:141 */       limpiar();
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  94:144 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  95:    */     }
/*  96:146 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String eliminar()
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:156 */       this.servicioUbicacionActivo.eliminar(this.ubicacionActivo);
/* 104:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 109:160 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 110:    */     }
/* 111:162 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:171 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String limpiar()
/* 120:    */   {
/* 121:180 */     crearUbicacionActivo();
/* 122:181 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public UbicacionActivo getUbicacionActivo()
/* 126:    */   {
/* 127:198 */     return this.ubicacionActivo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setUbicacionActivo(UbicacionActivo ubicacionActivo)
/* 131:    */   {
/* 132:208 */     this.ubicacionActivo = ubicacionActivo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public LazyDataModel<UbicacionActivo> getListaUbicacionActivo()
/* 136:    */   {
/* 137:217 */     return this.listaUbicacionActivo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setListaUbicacionActivo(LazyDataModel<UbicacionActivo> listaUbicacionActivo)
/* 141:    */   {
/* 142:227 */     this.listaUbicacionActivo = listaUbicacionActivo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List<Departamento> getListaDepartamentoCombo()
/* 146:    */   {
/* 147:236 */     if (this.listaDepartamentoCombo == null) {
/* 148:237 */       this.listaDepartamentoCombo = this.servicioDepartamento.obtenerListaCombo("nombre", true, null);
/* 149:    */     }
/* 150:239 */     return this.listaDepartamentoCombo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaDepartamentoCombo(List<Departamento> listaDepartamentoCombo)
/* 154:    */   {
/* 155:249 */     this.listaDepartamentoCombo = listaDepartamentoCombo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public DataTable getDtUbicacionActivo()
/* 159:    */   {
/* 160:258 */     return this.dtUbicacionActivo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setDtUbicacionActivo(DataTable dtUbicacionActivo)
/* 164:    */   {
/* 165:268 */     this.dtUbicacionActivo = dtUbicacionActivo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<Sucursal> getListaSucursalCombo()
/* 169:    */   {
/* 170:272 */     if (this.listaSucursalCombo == null) {
/* 171:273 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 172:    */     }
/* 173:275 */     return this.listaSucursalCombo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 177:    */   {
/* 178:279 */     this.listaSucursalCombo = listaSucursalCombo;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.configuracion.UbicacionActivoBean
 * JD-Core Version:    0.7.0.1
 */