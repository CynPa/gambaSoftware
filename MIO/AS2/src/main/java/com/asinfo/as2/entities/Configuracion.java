/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.EnumType;
/*   7:    */ import javax.persistence.Enumerated;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="configuracion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "parametro"})})
/*  21:    */ public class Configuracion
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="configuracion", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="configuracion")
/*  28:    */   @Column(name="id_configuracion", unique=true, nullable=false)
/*  29:    */   private int idConfiguracion;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Enumerated(EnumType.STRING)
/*  35:    */   @Column(name="parametro", nullable=false, length=50)
/*  36:    */   @NotNull
/*  37:    */   private Parametro parametro;
/*  38:    */   @Column(name="valor", nullable=false, length=300)
/*  39:    */   @NotNull
/*  40:    */   @Size(max=300)
/*  41:    */   private String valor;
/*  42:    */   @Column(name="valor_mostrar", nullable=false, length=300)
/*  43:    */   @NotNull
/*  44:    */   @Size(max=300)
/*  45:    */   private String valorMostrar;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_modulo", nullable=false)
/*  48:    */   private Modulo modulo;
/*  49:    */   
/*  50:    */   public Configuracion() {}
/*  51:    */   
/*  52:    */   public Configuracion(int idConfiguracion, Parametro parametro, String valor, String valorMostrar)
/*  53:    */   {
/*  54: 84 */     this.idConfiguracion = idConfiguracion;
/*  55: 85 */     this.parametro = parametro;
/*  56: 86 */     this.valor = valor;
/*  57: 87 */     this.valorMostrar = valorMostrar;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdConfiguracion()
/*  61:    */   {
/*  62: 91 */     return this.idConfiguracion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdConfiguracion(int idConfiguracion)
/*  66:    */   {
/*  67: 95 */     this.idConfiguracion = idConfiguracion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72: 99 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:103 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:107 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:111 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Parametro getParametro()
/*  91:    */   {
/*  92:115 */     return this.parametro;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setParametro(Parametro parametro)
/*  96:    */   {
/*  97:119 */     this.parametro = parametro;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getValor()
/* 101:    */   {
/* 102:123 */     return this.valor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setValor(String valor)
/* 106:    */   {
/* 107:127 */     this.valor = valor;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getValorMostrar()
/* 111:    */   {
/* 112:131 */     return this.valorMostrar;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setValorMostrar(String valorMostrar)
/* 116:    */   {
/* 117:135 */     if (valorMostrar.length() > 150) {
/* 118:136 */       valorMostrar = valorMostrar.substring(0, 150);
/* 119:    */     }
/* 120:138 */     this.valorMostrar = valorMostrar;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Modulo getModulo()
/* 124:    */   {
/* 125:147 */     return this.modulo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setModulo(Modulo modulo)
/* 129:    */   {
/* 130:157 */     this.modulo = modulo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getId()
/* 134:    */   {
/* 135:167 */     return getIdConfiguracion();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String toString()
/* 139:    */   {
/* 140:172 */     return this.parametro.getNombre();
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Configuracion
 * JD-Core Version:    0.7.0.1
 */