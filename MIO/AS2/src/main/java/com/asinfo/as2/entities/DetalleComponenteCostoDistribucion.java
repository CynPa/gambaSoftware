/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="detalle_componente_costo_distribucion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_componente_costo", "id_ruta_fabricacion", "id_dimension_contable1", "id_dimension_contable2", "id_dimension_contable3", "id_dimension_contable4", "id_dimension_contable5"})})
/*  18:    */ public class DetalleComponenteCostoDistribucion
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="detalle_componente_costo_distribucion", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_componente_costo_distribucion")
/*  25:    */   @Column(name="id_detalle_componente_costo_distribucion")
/*  26:    */   private int idDetalleComponenteCostoDistribucion;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   @NotNull
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   @NotNull
/*  32:    */   private int idSucursal;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_componente_costo", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private ComponenteCosto componenteCosto;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_dimension_contable1")
/*  39:    */   private DimensionContable dimensionContable1;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_dimension_contable2")
/*  42:    */   private DimensionContable dimensionContable2;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_dimension_contable3")
/*  45:    */   private DimensionContable dimensionContable3;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_dimension_contable4")
/*  48:    */   private DimensionContable dimensionContable4;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_dimension_contable5")
/*  51:    */   private DimensionContable dimensionContable5;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_ruta_fabricacion", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private RutaFabricacion rutaFabricacion;
/*  56:    */   
/*  57:    */   public int getId()
/*  58:    */   {
/*  59: 91 */     return this.idDetalleComponenteCostoDistribucion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setId(int idDetalleComponenteCostoDistribucion)
/*  63:    */   {
/*  64: 96 */     this.idDetalleComponenteCostoDistribucion = idDetalleComponenteCostoDistribucion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdDetalleComponenteCostoDistribucion()
/*  68:    */   {
/*  69:100 */     return this.idDetalleComponenteCostoDistribucion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdDetalleComponenteCostoDistribucion(int idDetalleComponenteCostoDistribucion)
/*  73:    */   {
/*  74:104 */     this.idDetalleComponenteCostoDistribucion = idDetalleComponenteCostoDistribucion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ComponenteCosto getComponenteCosto()
/*  78:    */   {
/*  79:108 */     return this.componenteCosto;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setComponenteCosto(ComponenteCosto componenteCosto)
/*  83:    */   {
/*  84:112 */     this.componenteCosto = componenteCosto;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public DimensionContable getDimensionContable1()
/*  88:    */   {
/*  89:116 */     return this.dimensionContable1;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/*  93:    */   {
/*  94:120 */     this.dimensionContable1 = dimensionContable1;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public DimensionContable getDimensionContable2()
/*  98:    */   {
/*  99:124 */     return this.dimensionContable2;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 103:    */   {
/* 104:128 */     this.dimensionContable2 = dimensionContable2;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public DimensionContable getDimensionContable3()
/* 108:    */   {
/* 109:132 */     return this.dimensionContable3;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 113:    */   {
/* 114:136 */     this.dimensionContable3 = dimensionContable3;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public DimensionContable getDimensionContable4()
/* 118:    */   {
/* 119:140 */     return this.dimensionContable4;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 123:    */   {
/* 124:144 */     this.dimensionContable4 = dimensionContable4;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public DimensionContable getDimensionContable5()
/* 128:    */   {
/* 129:148 */     return this.dimensionContable5;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 133:    */   {
/* 134:152 */     this.dimensionContable5 = dimensionContable5;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public RutaFabricacion getRutaFabricacion()
/* 138:    */   {
/* 139:156 */     return this.rutaFabricacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/* 143:    */   {
/* 144:160 */     this.rutaFabricacion = rutaFabricacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getIdOrganizacion()
/* 148:    */   {
/* 149:164 */     return this.idOrganizacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIdOrganizacion(int idOrganizacion)
/* 153:    */   {
/* 154:168 */     this.idOrganizacion = idOrganizacion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getIdSucursal()
/* 158:    */   {
/* 159:172 */     return this.idSucursal;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdSucursal(int idSucursal)
/* 163:    */   {
/* 164:176 */     this.idSucursal = idSucursal;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleComponenteCostoDistribucion
 * JD-Core Version:    0.7.0.1
 */