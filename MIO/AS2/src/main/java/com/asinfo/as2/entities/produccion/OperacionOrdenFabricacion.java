/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.DecimalMin;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import org.hibernate.annotations.ColumnDefault;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="operacion_orden_fabricacion")
/*  24:    */ public class OperacionOrdenFabricacion
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="operacion_orden_fabricacion", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="operacion_orden_fabricacion")
/*  32:    */   @Column(name="id_operacion_orden_fabricacion")
/*  33:    */   private int idOperacionOrdenFabricacion;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="horas_hombre", nullable=true, precision=12, scale=2)
/*  39:    */   @Digits(integer=10, fraction=2)
/*  40:    */   @DecimalMin("0.00")
/*  41: 62 */   private BigDecimal horasHombre = BigDecimal.ZERO;
/*  42:    */   @Column(name="horas_maquina", nullable=true, precision=12, scale=2)
/*  43:    */   @Digits(integer=10, fraction=2)
/*  44:    */   @DecimalMin("0.00")
/*  45: 67 */   private BigDecimal horasMaquina = BigDecimal.ZERO;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_operacion_produccion", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private OperacionProduccion operacionProduccion;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_orden_fabricacion", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private OrdenFabricacion ordenFabricacion;
/*  54:    */   @Column(name="anio", nullable=false)
/*  55:    */   @Min(0L)
/*  56:    */   @ColumnDefault("0")
/*  57:    */   private int anio;
/*  58:    */   @Column(name="mes", nullable=false)
/*  59:    */   @Min(0L)
/*  60:    */   @ColumnDefault("0")
/*  61:    */   private int mes;
/*  62:    */   
/*  63:    */   public int getIdOperacionOrdenFabricacion()
/*  64:    */   {
/*  65:108 */     return this.idOperacionOrdenFabricacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOperacionOrdenFabricacion(int idOperacionOrdenFabricacion)
/*  69:    */   {
/*  70:118 */     this.idOperacionOrdenFabricacion = idOperacionOrdenFabricacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:127 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:137 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:146 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:156 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getId()
/*  94:    */   {
/*  95:166 */     return this.idOperacionOrdenFabricacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public BigDecimal getHorasHombre()
/*  99:    */   {
/* 100:170 */     return this.horasHombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setHorasHombre(BigDecimal horasHombre)
/* 104:    */   {
/* 105:174 */     this.horasHombre = horasHombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public BigDecimal getHorasMaquina()
/* 109:    */   {
/* 110:178 */     return this.horasMaquina;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setHorasMaquina(BigDecimal horasMaquina)
/* 114:    */   {
/* 115:182 */     this.horasMaquina = horasMaquina;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public OperacionProduccion getOperacionProduccion()
/* 119:    */   {
/* 120:186 */     return this.operacionProduccion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setOperacionProduccion(OperacionProduccion operacionProduccion)
/* 124:    */   {
/* 125:190 */     this.operacionProduccion = operacionProduccion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public OrdenFabricacion getOrdenFabricacion()
/* 129:    */   {
/* 130:194 */     return this.ordenFabricacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 134:    */   {
/* 135:198 */     this.ordenFabricacion = ordenFabricacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getMes()
/* 139:    */   {
/* 140:202 */     return this.mes;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setMes(int mes)
/* 144:    */   {
/* 145:206 */     this.mes = mes;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getAnio()
/* 149:    */   {
/* 150:210 */     return this.anio;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setAnio(int anio)
/* 154:    */   {
/* 155:214 */     this.anio = anio;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.OperacionOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */