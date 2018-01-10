/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DimensionContable;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ProyectoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1461837273230255977L;
/*  30:    */   @EJB
/*  31:    */   private ServicioDimensionContable servicioProyecto;
/*  32:    */   private DimensionContable proyecto;
/*  33:    */   private LazyDataModel<DimensionContable> listaProyecto;
/*  34:    */   private DataTable dtProyecto;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 66 */     this.listaProyecto = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<DimensionContable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 73 */         List<DimensionContable> lista = new ArrayList();
/*  46: 74 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47: 75 */         filters.put("numero", "5");
/*  48:    */         
/*  49: 77 */         lista = ProyectoBean.this.servicioProyecto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50: 78 */         ProyectoBean.this.listaProyecto.setRowCount(ProyectoBean.this.servicioProyecto.contarPorCriterio(filters));
/*  51:    */         
/*  52: 80 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String editar()
/*  58:    */   {
/*  59: 93 */     if ((getProyecto() != null) && (getProyecto().getIdDimensionContable() != 0)) {
/*  60: 94 */       setEditado(true);
/*  61:    */     } else {
/*  62: 96 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  63:    */     }
/*  64: 99 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String limpiar()
/*  68:    */   {
/*  69:109 */     crearProyecto();
/*  70:    */     
/*  71:111 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:122 */       this.proyecto.setMascara(FuncionesUtiles.replicar('0', this.proyecto.getCodigo().trim().length()) + ".");
/*  79:    */       
/*  80:124 */       this.proyecto.setListaCuentaContableDimensionContable(new ArrayList());
/*  81:    */       
/*  82:126 */       this.servicioProyecto.guardar(this.proyecto);
/*  83:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  84:128 */       limpiar();
/*  85:129 */       setEditado(false);
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  90:133 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  91:    */     }
/*  92:136 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String eliminar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:147 */       this.servicioProyecto.eliminar(this.proyecto);
/* 100:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:151 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */     }
/* 107:154 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarDatos()
/* 111:    */   {
/* 112:164 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void crearProyecto()
/* 116:    */   {
/* 117:173 */     this.proyecto = new DimensionContable();
/* 118:174 */     this.proyecto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 119:175 */     this.proyecto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 120:    */     
/* 121:177 */     this.proyecto.setNumero("5");
/* 122:178 */     this.proyecto.setTipoAccesoContable(TipoAccesoContable.BLOQUEADA);
/* 123:179 */     this.proyecto.setActivo(true);
/* 124:180 */     this.proyecto.setIndicadorMovimiento(true);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public DimensionContable getProyecto()
/* 128:    */   {
/* 129:190 */     return this.proyecto;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setProyecto(DimensionContable proyecto)
/* 133:    */   {
/* 134:200 */     this.proyecto = proyecto;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public LazyDataModel<DimensionContable> getListaProyecto()
/* 138:    */   {
/* 139:210 */     return this.listaProyecto;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListaProyecto(LazyDataModel<DimensionContable> listaProyecto)
/* 143:    */   {
/* 144:220 */     this.listaProyecto = listaProyecto;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public DataTable getDtProyecto()
/* 148:    */   {
/* 149:224 */     return this.dtProyecto;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDtProyecto(DataTable dtProyecto)
/* 153:    */   {
/* 154:228 */     this.dtProyecto = dtProyecto;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ProyectoBean
 * JD-Core Version:    0.7.0.1
 */