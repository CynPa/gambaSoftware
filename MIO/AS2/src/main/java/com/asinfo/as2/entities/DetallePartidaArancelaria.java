/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_partida_arancelaria")
/*  23:    */ public class DetallePartidaArancelaria
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -4439423722247410800L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_partida_arancelaria", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_partida_arancelaria")
/*  30:    */   @Column(name="id_detalle_partida_arancelaria")
/*  31:    */   private int idDetallePartidaArancelaria;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   @Column(name="fecha_desde", nullable=true)
/*  38:    */   @NotNull
/*  39:    */   private Date fechaDesde;
/*  40:    */   @Temporal(TemporalType.DATE)
/*  41:    */   @Column(name="fecha_hasta", nullable=true)
/*  42:    */   @NotNull
/*  43:    */   private Date fechaHasta;
/*  44:    */   @Column(name="descripcion", length=200, nullable=true)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="porcentaje", nullable=false, precision=12, scale=2)
/*  48:    */   @Min(0L)
/*  49:    */   private BigDecimal porcentaje;
/*  50:    */   @Column(name="valor_base", nullable=false, precision=12, scale=2)
/*  51:    */   @Min(0L)
/*  52:    */   private BigDecimal valorBase;
/*  53:    */   @Column(name="activo", nullable=false)
/*  54:    */   private boolean activo;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_partida_arancelaria", nullable=true)
/*  57:    */   private PartidaArancelaria partidaArancelaria;
/*  58:    */   
/*  59:    */   public int getId()
/*  60:    */   {
/*  61:113 */     return getIdDetallePartidaArancelaria();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdDetallePartidaArancelaria()
/*  65:    */   {
/*  66:122 */     return this.idDetallePartidaArancelaria;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdDetallePartidaArancelaria(int idDetallePartidaArancelaria)
/*  70:    */   {
/*  71:132 */     this.idDetallePartidaArancelaria = idDetallePartidaArancelaria;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdOrganizacion()
/*  75:    */   {
/*  76:141 */     return this.idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdOrganizacion(int idOrganizacion)
/*  80:    */   {
/*  81:151 */     this.idOrganizacion = idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdSucursal()
/*  85:    */   {
/*  86:160 */     return this.idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdSucursal(int idSucursal)
/*  90:    */   {
/*  91:170 */     this.idSucursal = idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Date getFechaDesde()
/*  95:    */   {
/*  96:179 */     return this.fechaDesde;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setFechaDesde(Date fechaDesde)
/* 100:    */   {
/* 101:189 */     this.fechaDesde = fechaDesde;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Date getFechaHasta()
/* 105:    */   {
/* 106:198 */     return this.fechaHasta;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setFechaHasta(Date fechaHasta)
/* 110:    */   {
/* 111:208 */     this.fechaHasta = fechaHasta;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:217 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:227 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public PartidaArancelaria getPartidaArancelaria()
/* 125:    */   {
/* 126:236 */     return this.partidaArancelaria;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPartidaArancelaria(PartidaArancelaria partidaArancelaria)
/* 130:    */   {
/* 131:246 */     this.partidaArancelaria = partidaArancelaria;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getPorcentaje()
/* 135:    */   {
/* 136:255 */     return this.porcentaje;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 140:    */   {
/* 141:265 */     this.porcentaje = porcentaje;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getDescripcion()
/* 145:    */   {
/* 146:274 */     return this.descripcion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDescripcion(String descripcion)
/* 150:    */   {
/* 151:284 */     this.descripcion = descripcion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getValorBase()
/* 155:    */   {
/* 156:293 */     return this.valorBase;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setValorBase(BigDecimal valorBase)
/* 160:    */   {
/* 161:303 */     this.valorBase = valorBase;
/* 162:    */   }
/* 163:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePartidaArancelaria
 * JD-Core Version:    0.7.0.1
 */