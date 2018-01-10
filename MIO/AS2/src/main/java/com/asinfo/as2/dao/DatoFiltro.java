/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.OperacionEnum;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.text.ParseException;
/*   6:    */ import java.text.SimpleDateFormat;
/*   7:    */ import java.util.Date;
/*   8:    */ import javax.persistence.criteria.CriteriaBuilder;
/*   9:    */ import javax.persistence.criteria.Expression;
/*  10:    */ import javax.persistence.criteria.From;
/*  11:    */ 
/*  12:    */ public final class DatoFiltro<T extends Comparable<T>>
/*  13:    */ {
/*  14:    */   public static final String FORMATO_FECHA = "dd/MM/yyyy";
/*  15: 31 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
/*  16:    */   private String propiedadFiltro;
/*  17:    */   private OperacionEnum operacion;
/*  18:    */   private String strValorFiltro;
/*  19:    */   private Object valor;
/*  20:    */   private Object valor2;
/*  21:    */   private Class<?> tipoDato;
/*  22:    */   private Expression<T> expresion;
/*  23:    */   From<?, ?> from;
/*  24:    */   
/*  25:    */   public DatoFiltro(Class<?> tipoDato, From<?, ?> from, String propiedadFiltro, String strValorFiltro)
/*  26:    */   {
/*  27: 44 */     this.propiedadFiltro = propiedadFiltro;
/*  28: 45 */     this.strValorFiltro = strValorFiltro;
/*  29: 46 */     this.tipoDato = tipoDato;
/*  30: 47 */     this.from = from;
/*  31: 48 */     this.expresion = from.get(propiedadFiltro);
/*  32: 49 */     setearValores();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Expression<T> getExpression()
/*  36:    */   {
/*  37: 53 */     return this.expresion;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setearValores()
/*  41:    */   {
/*  42: 61 */     this.strValorFiltro = this.strValorFiltro.trim().replaceAll("^ +| +$|( )+", "$1");
/*  43: 63 */     if ((this.strValorFiltro.startsWith("!=")) || (this.strValorFiltro.startsWith("<>")))
/*  44:    */     {
/*  45: 64 */       this.operacion = OperacionEnum.DIFERENTE;
/*  46: 65 */       this.strValorFiltro = this.strValorFiltro.substring(2);
/*  47:    */     }
/*  48: 67 */     else if (this.strValorFiltro.startsWith("<>"))
/*  49:    */     {
/*  50: 68 */       this.operacion = OperacionEnum.DIFERENTE;
/*  51: 69 */       this.strValorFiltro = this.strValorFiltro.substring(2);
/*  52:    */     }
/*  53: 71 */     else if (this.strValorFiltro.startsWith(">="))
/*  54:    */     {
/*  55: 72 */       this.operacion = OperacionEnum.MAYOR_IGUAL;
/*  56: 73 */       this.strValorFiltro = this.strValorFiltro.substring(2);
/*  57:    */     }
/*  58: 75 */     else if (this.strValorFiltro.startsWith("<="))
/*  59:    */     {
/*  60: 76 */       this.operacion = OperacionEnum.MENOR_IGUAL;
/*  61: 77 */       this.strValorFiltro = this.strValorFiltro.substring(2);
/*  62:    */     }
/*  63: 79 */     else if (this.strValorFiltro.startsWith(">"))
/*  64:    */     {
/*  65: 80 */       this.operacion = OperacionEnum.MAYOR;
/*  66: 81 */       this.strValorFiltro = this.strValorFiltro.substring(1);
/*  67:    */     }
/*  68: 83 */     else if (this.strValorFiltro.startsWith("<"))
/*  69:    */     {
/*  70: 84 */       this.operacion = OperacionEnum.MENOR;
/*  71: 85 */       this.strValorFiltro = this.strValorFiltro.substring(1);
/*  72:    */     }
/*  73: 87 */     else if (this.strValorFiltro.startsWith("="))
/*  74:    */     {
/*  75: 88 */       this.operacion = OperacionEnum.IGUAL;
/*  76: 89 */       this.strValorFiltro = this.strValorFiltro.substring(1);
/*  77:    */     }
/*  78: 91 */     else if ("IS_NOT_NULL".equalsIgnoreCase(this.strValorFiltro))
/*  79:    */     {
/*  80: 92 */       this.operacion = OperacionEnum.IS_NOT_NULL;
/*  81:    */     }
/*  82: 94 */     else if ("IS_NULL".equalsIgnoreCase(this.strValorFiltro))
/*  83:    */     {
/*  84: 95 */       this.operacion = OperacionEnum.IS_NULL;
/*  85:    */     }
/*  86: 97 */     else if (this.strValorFiltro.startsWith("%"))
/*  87:    */     {
/*  88: 98 */       this.operacion = OperacionEnum.LIKE;
/*  89:    */     }
/*  90:100 */     else if (this.strValorFiltro.startsWith(OperacionEnum.BETWEEN.name()))
/*  91:    */     {
/*  92:101 */       this.operacion = OperacionEnum.BETWEEN;
/*  93:102 */       this.strValorFiltro = this.strValorFiltro.replace(OperacionEnum.BETWEEN.name(), "");
/*  94:    */     }
/*  95:104 */     else if (this.strValorFiltro.endsWith("%"))
/*  96:    */     {
/*  97:105 */       this.strValorFiltro = this.strValorFiltro.substring(0, this.strValorFiltro.length() - 1);
/*  98:106 */       this.operacion = OperacionEnum.LIKE;
/*  99:    */     }
/* 100:109 */     else if (this.tipoDato == String.class)
/* 101:    */     {
/* 102:110 */       this.operacion = OperacionEnum.LIKE;
/* 103:111 */       this.strValorFiltro = ("%" + this.strValorFiltro);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:113 */       this.operacion = OperacionEnum.IGUAL;
/* 108:    */     }
/* 109:119 */     this.strValorFiltro = this.strValorFiltro.replaceAll("^ +| +$|( )+", "$1");
/* 110:121 */     if (this.tipoDato == String.class) {
/* 111:122 */       this.valor = this.strValorFiltro;
/* 112:124 */     } else if ((this.tipoDato == Integer.class) || (this.tipoDato.toString().equals("int"))) {
/* 113:125 */       this.valor = new Integer(Integer.parseInt(this.strValorFiltro));
/* 114:127 */     } else if ((this.tipoDato == Long.class) || (this.tipoDato.toString().equals("long"))) {
/* 115:128 */       this.valor = new Long(Long.parseLong(this.strValorFiltro));
/* 116:130 */     } else if (this.tipoDato == BigDecimal.class) {
/* 117:131 */       this.valor = new BigDecimal(this.strValorFiltro);
/* 118:133 */     } else if ((this.tipoDato == Boolean.class) || (this.tipoDato.toString().equals("boolean"))) {
/* 119:134 */       this.valor = Boolean.valueOf(this.strValorFiltro);
/* 120:136 */     } else if (this.tipoDato == Date.class) {
/* 121:    */       try
/* 122:    */       {
/* 123:138 */         String[] arrayFechas = this.strValorFiltro.split("~");
/* 124:139 */         if (arrayFechas.length > 1) {
/* 125:140 */           this.valor2 = dateFormat.parse(arrayFechas[1]);
/* 126:    */         }
/* 127:143 */         this.valor = dateFormat.parse(arrayFechas[0]);
/* 128:    */       }
/* 129:    */       catch (ParseException e)
/* 130:    */       {
/* 131:145 */         this.valor = new Date();
/* 132:    */       }
/* 133:148 */     } else if (this.tipoDato.isEnum()) {
/* 134:149 */       this.valor = Enum.valueOf(this.tipoDato, this.strValorFiltro);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Expression<?> getExpresion(CriteriaBuilder criteriaBuilder, DatoFiltro<T> datoFiltro)
/* 139:    */   {
/* 140:155 */     if (datoFiltro == null) {
/* 141:156 */       return null;
/* 142:    */     }
/* 143:159 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$OperacionEnum[datoFiltro.getOperacion().ordinal()])
/* 144:    */     {
/* 145:    */     case 1: 
/* 146:162 */       return criteriaBuilder.equal(datoFiltro.getExpression(), datoFiltro.getValor());
/* 147:    */     case 2: 
/* 148:165 */       return criteriaBuilder.notEqual(datoFiltro.getExpression(), datoFiltro.getValor());
/* 149:    */     case 3: 
/* 150:169 */       return criteriaBuilder.like(criteriaBuilder.lower(datoFiltro.getExpression().as(String.class)), String.valueOf(datoFiltro.getValor())
/* 151:170 */         .toLowerCase() + "%");
/* 152:    */     case 4: 
/* 153:173 */       return criteriaBuilder.lessThan(datoFiltro.getExpression(), datoFiltro.getValor());
/* 154:    */     case 5: 
/* 155:176 */       return criteriaBuilder.lessThanOrEqualTo(datoFiltro.getExpression(), datoFiltro.getValor());
/* 156:    */     case 6: 
/* 157:179 */       return criteriaBuilder.greaterThan(datoFiltro.getExpression(), datoFiltro.getValor());
/* 158:    */     case 7: 
/* 159:182 */       return criteriaBuilder.greaterThanOrEqualTo(datoFiltro.getExpression(), datoFiltro.getValor());
/* 160:    */     case 8: 
/* 161:185 */       return criteriaBuilder.isNull(datoFiltro.getExpression());
/* 162:    */     case 9: 
/* 163:188 */       return criteriaBuilder.isNotNull(datoFiltro.getExpression());
/* 164:    */     case 10: 
/* 165:191 */       return criteriaBuilder.between(datoFiltro.getExpression(), datoFiltro.getValor(), datoFiltro.getValor2());
/* 166:    */     }
/* 167:194 */     return null;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public T getValor()
/* 171:    */   {
/* 172:201 */     return (Comparable)this.valor;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public OperacionEnum getOperacion()
/* 176:    */   {
/* 177:205 */     return this.operacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getPropiedadFiltro()
/* 181:    */   {
/* 182:209 */     return this.propiedadFiltro;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public T getValor2()
/* 186:    */   {
/* 187:214 */     return (Comparable)this.valor2;
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DatoFiltro
 * JD-Core Version:    0.7.0.1
 */