/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.OneToMany;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="rango_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  22:    */ public class RangoComision
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="rango_comision", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rango_comision")
/*  29:    */   @Column(name="id_rango_comision")
/*  30:    */   private int idRangoComision;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=10)
/*  36:    */   @Size(min=2, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @Size(min=2, max=50)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", nullable=true, length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="valor_desde", nullable=false, precision=12, scale=2)
/*  47:    */   @NotNull
/*  48:    */   @Digits(integer=12, fraction=2)
/*  49:    */   @Min(0L)
/*  50:    */   private BigDecimal valorDesde;
/*  51:    */   @Column(name="valor_hasta", nullable=false, precision=12, scale=2)
/*  52:    */   @NotNull
/*  53:    */   @Digits(integer=12, fraction=2)
/*  54:    */   @Min(0L)
/*  55:    */   private BigDecimal valorHasta;
/*  56:    */   @OneToMany(mappedBy="rangoComision", fetch=FetchType.LAZY)
/*  57: 89 */   private List<RangoComisionCategoriaProducto> listaRangoComisionCategoriaProducto = new ArrayList();
/*  58:    */   
/*  59:    */   public RangoComision() {}
/*  60:    */   
/*  61:    */   public RangoComision(int idRangoComision, String codigo, String nombre)
/*  62:    */   {
/*  63:105 */     this.idRangoComision = idRangoComision;
/*  64:106 */     this.codigo = codigo;
/*  65:107 */     this.nombre = nombre;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdRangoComision()
/*  69:    */   {
/*  70:118 */     return this.idRangoComision;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdRangoComision(int idRangoComision)
/*  74:    */   {
/*  75:125 */     this.idRangoComision = idRangoComision;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80:132 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85:139 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdSucursal()
/*  89:    */   {
/*  90:146 */     return this.idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdSucursal(int idSucursal)
/*  94:    */   {
/*  95:153 */     this.idSucursal = idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getCodigo()
/*  99:    */   {
/* 100:160 */     return this.codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setCodigo(String codigo)
/* 104:    */   {
/* 105:167 */     this.codigo = codigo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getNombre()
/* 109:    */   {
/* 110:174 */     return this.nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setNombre(String nombre)
/* 114:    */   {
/* 115:181 */     this.nombre = nombre;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getDescripcion()
/* 119:    */   {
/* 120:188 */     return this.descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDescripcion(String descripcion)
/* 124:    */   {
/* 125:195 */     this.descripcion = descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isActivo()
/* 129:    */   {
/* 130:202 */     return this.activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivo(boolean activo)
/* 134:    */   {
/* 135:209 */     this.activo = activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public BigDecimal getValorDesde()
/* 139:    */   {
/* 140:216 */     return this.valorDesde;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setValorDesde(BigDecimal valorDesde)
/* 144:    */   {
/* 145:223 */     this.valorDesde = valorDesde;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public BigDecimal getValorHasta()
/* 149:    */   {
/* 150:230 */     return this.valorHasta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setValorHasta(BigDecimal valorHasta)
/* 154:    */   {
/* 155:237 */     this.valorHasta = valorHasta;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getId()
/* 159:    */   {
/* 160:245 */     return this.idRangoComision;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<RangoComisionCategoriaProducto> getListaRangoComisionCategoriaProducto()
/* 164:    */   {
/* 165:249 */     return this.listaRangoComisionCategoriaProducto;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setListaRangoComisionCategoriaProducto(List<RangoComisionCategoriaProducto> listaRangoComisionCategoriaProducto)
/* 169:    */   {
/* 170:254 */     this.listaRangoComisionCategoriaProducto = listaRangoComisionCategoriaProducto;
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RangoComision
 * JD-Core Version:    0.7.0.1
 */